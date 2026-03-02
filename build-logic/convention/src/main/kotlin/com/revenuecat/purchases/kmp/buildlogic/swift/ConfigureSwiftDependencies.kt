package com.revenuecat.purchases.kmp.buildlogic.swift

import com.revenuecat.purchases.kmp.buildlogic.swift.model.SwiftDependency
import com.revenuecat.purchases.kmp.buildlogic.swift.model.SwiftPackageInfo
import com.revenuecat.purchases.kmp.buildlogic.swift.model.getSwiftPackageInfo
import com.revenuecat.purchases.kmp.buildlogic.swift.task.GenerateDefFileTask
import com.revenuecat.purchases.kmp.buildlogic.swift.task.ProcessSwiftResourcesTask
import com.revenuecat.purchases.kmp.buildlogic.swift.task.SwiftBuildTask
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.withType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.resources.ResourcesExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.KonanTarget
import java.io.File

/**
 * Registers the Swift package registry and configures Swift dependencies.
 */
fun Project.configureSwiftDependencies() {
    val registry = getOrCreateSwiftPackageRegistry()

    afterEvaluate {
        if (registry.packages.isNotEmpty()) {
            configureAllSwiftDependencies(registry.packages)
        }
    }
}

private fun Project.configureAllSwiftDependencies(dependencies: List<SwiftDependency>) {
    val kotlin = extensions.findByType(KotlinMultiplatformExtension::class.java)
        ?: error("Kotlin Multiplatform plugin must be applied before configuring Swift dependencies")

    val packageInfos = dependencies
        .groupBy { it.packageDir }
        .mapValues { (packageDir, _) -> getSwiftPackageInfo(packageDir) }

    val resourcesConfigured = mutableSetOf<String>()
    dependencies.forEach { dependency ->
        val packageInfo = packageInfos[dependency.packageDir]
            ?: error("Could not find package info for ${dependency.packageDir}")
        val targetSourceDir = packageInfo.getTargetSourceDir(dependency.target, dependency.packageDir)
        val swiftDependencies = packageInfo.getTargetDependencies(dependency.target)
        
        configureSwiftDependency(kotlin, dependency, targetSourceDir, swiftDependencies)
        
        if (dependency.target !in resourcesConfigured) {
            configureSwiftResources(dependency, packageInfo)
            resourcesConfigured.add(dependency.target)
        }
    }
}

/**
 * Configures Swift package resources processing to include in the .klib via Compose Resources.
 */
private fun Project.configureSwiftResources(
    dependency: SwiftDependency,
    packageInfo: SwiftPackageInfo
) {
    val resources = packageInfo.getTargetResources(dependency.target)
    if (resources.isEmpty()) return

    val composeExtension = extensions.findByType(ComposeExtension::class.java)
    
    if (composeExtension == null) {
        logger.info(
            "Skipping ${resources.size} resources in Swift target '${dependency.target}' " +
                    "because the Compose Gradle plugin is not applied."
        )
        return
    }
    
    val resourcesExtension = (composeExtension as ExtensionAware).extensions
        .findByType(ResourcesExtension::class.java)
    
    if (resourcesExtension == null) {
        logger.info(
            "Skipping ${resources.size} resources in Swift target '${dependency.target}' " +
                    "because the Compose Resources extension was not found."
        )
        return
    }
    
    val taskName = "processSwiftResources${dependency.target}"
    val processResourcesTask = tasks.register(taskName, ProcessSwiftResourcesTask::class.java) {
        platformVersions.set(packageInfo.platformVersions)
        this.resources.set(resources)
        sourceSetName.set(dependency.sourceSetName)
        outputDir.set(layout.buildDirectory.dir("swift-resources/${dependency.target}"))
        resourceFiles.from(resources.map { File(it.path) })
    }
    with(resourcesExtension) {
        packageOfResClass = "${dependency.packageName}.resources"
        customDirectory(
            sourceSetName = dependency.sourceSetName,
            directoryProvider = processResourcesTask.map { it.outputDir.get() }
        )
    }
}

private fun Project.configureSwiftDependency(
    kotlin: KotlinMultiplatformExtension,
    dependency: SwiftDependency,
    targetSourceDir: File,
    swiftDependencies: List<String>
) {
    // Register a task to generate the cinterop .def file
    val defFile = layout.buildDirectory.file("generated/cinterop/${dependency.target}.def")
    val generateDefTaskName = "generateCinteropDef${dependency.target}"
    val generateDefTask = tasks.register(generateDefTaskName, GenerateDefFileTask::class.java) {
        packageName.set(dependency.packageName)
        moduleName.set(dependency.moduleName)
        libraryName.set(dependency.libraryName)
        dependency.customDeclarations?.let { customDeclarations.set(it) }
        this.toolchainPath.set(getToolchainPath())
        this.swiftSourceDir.set(targetSourceDir)
        this.defFile.set(defFile)
    }

    // Find which Swift dependencies are owned by other projects
    val globalRegistry = getOrCreateGlobalSwiftRegistry()
    val moduleDependencies = swiftDependencies.mapNotNull { depTarget ->
        globalRegistry.findOwner(depTarget)
            // Only include dependencies from other projects
            ?.takeIf { it.project != this  }
    }

    kotlin.targets.withType<KotlinNativeTarget> {
        // Skip this target if it doesn't include the source set this dependency is added to.
        if (!includesSourceSet(dependency.sourceSetName)) return@withType

        if (!konanTarget.isAppleTarget()) error(
            "swiftPackage() was called from source set '${dependency.sourceSetName}', " +
                    "but only Apple targets are supported."
        )

        val mainCompilation = compilations.getByName("main")
        val swiftBuildTask = registerSwiftBuildTask(this, dependency, targetSourceDir)
        val swiftOutputDir = layout.buildDirectory
            .dir("swift-packages/${dependency.target}/${konanTarget.name}")
            .get().asFile

        val sdkPath = getSdkPath(konanTarget)

        // Collect module paths: our own output + outputs from Swift dependencies in other projects
        val dependencyModulePaths = moduleDependencies.map { dep ->
            dep.project.layout.buildDirectory
                .dir("swift-packages/${dep.dependency.target}/${konanTarget.name}")
                .get().asFile
        }
        val allModulePaths = listOf(swiftOutputDir) + dependencyModulePaths

        mainCompilation.cinterops.create(dependency.target) {
            defFile(defFile)
            extraOpts("-libraryPath", swiftOutputDir.absolutePath)

            // Add -I flags for all module paths (own + dependencies)
            compilerOpts(
                "-fmodules",
                "-isysroot", sdkPath,
                *allModulePaths.flatMap { listOf("-I", it.absolutePath) }.toTypedArray()
            )

            tasks.named(interopProcessingTaskName).configure {
                // Make sure we generate the .def file and compile Swift before cinterop runs.
                dependsOn(generateDefTask)
                dependsOn(swiftBuildTask)

                // We're not configuring the static library as input because somehow it causes build 
                // failures when building debug after release (e.g. publishToMavenLocal). It seems to 
                // have something to do with the cinterop commonizer.
                // As a workaround we're adding the static library's checksum to the def file in GenerateDefFileTask.
                // This has the same effect of picking up any changes in Swift regardless of whether they're public.

                // Add task dependencies for Swift builds from other projects
                moduleDependencies.forEach { dep ->
                    val taskSuffix = getTaskSuffix(konanTarget)
                    val depTaskName = "compileSwift${dep.dependency.target}$taskSuffix"
                    dependsOn(dep.project.tasks.named(depTaskName))
                }
            }
        }
    }
}

private fun KonanTarget.isAppleTarget(): Boolean =
    this in listOf(
        // iOS
        KonanTarget.IOS_ARM64,
        KonanTarget.IOS_SIMULATOR_ARM64,
        KonanTarget.IOS_X64,
        // macOS
        KonanTarget.MACOS_ARM64,
        KonanTarget.MACOS_X64,
        // tvOS
        KonanTarget.TVOS_ARM64,
        KonanTarget.TVOS_SIMULATOR_ARM64,
        KonanTarget.TVOS_X64,
        // watchOS
        KonanTarget.WATCHOS_ARM64,
        KonanTarget.WATCHOS_SIMULATOR_ARM64,
        KonanTarget.WATCHOS_X64,
        KonanTarget.WATCHOS_DEVICE_ARM64,
    )

/**
 * Determines if a target should include a source set based on Kotlin Multiplatform naming
 * conventions.
 */
private fun KotlinNativeTarget.includesSourceSet(sourceSetName: String): Boolean {
    val targetName = this.name

    if (sourceSetName == "${targetName}Main") return true

    return when (sourceSetName) {
        "appleMain" -> targetName.startsWith("ios") ||
                targetName.startsWith("macos") ||
                targetName.startsWith("tvos") ||
                targetName.startsWith("watchos")

        "iosMain" -> targetName.startsWith("ios")
        "macosMain" -> targetName.startsWith("macos")
        "tvosMain" -> targetName.startsWith("tvos")
        "watchosMain" -> targetName.startsWith("watchos")
        else -> false
    }
}

private fun Project.registerSwiftBuildTask(
    kotlinTarget: KotlinNativeTarget,
    dependency: SwiftDependency,
    targetSourceDir: File
): TaskProvider<SwiftBuildTask> {
    val taskSuffix = getTaskSuffix(kotlinTarget.konanTarget)
    val taskName = "compileSwift${dependency.target}$taskSuffix"

    return tasks.register(taskName, SwiftBuildTask::class.java) {
        swiftTarget.set(dependency.target)
        sdk.set(kotlinTarget.konanTarget.getSdkName())
        triple.set(kotlinTarget.konanTarget.getTriple())
        headerName.set(dependency.headerName)
        libraryName.set(dependency.libraryName)
        moduleName.set(dependency.moduleName)
        configuration.set(getSwiftConfiguration())
        packageDir.set(dependency.packageDir)
        this.targetSourceDir.set(targetSourceDir)
        outputDir.set(layout.buildDirectory.dir("swift-packages/${dependency.target}/${kotlinTarget.konanTarget.name}"))
        // Use a shared scratch directory at root project level for SPM build cache sharing
        scratchDir.set(rootProject.layout.buildDirectory.dir("swift-packages/.build"))
        swiftSettingsArgs.set(dependency.swiftSettings?.toCommandLineArgs() ?: emptyList())
    }
}

/**
 * Get the task name suffix for a given Konan target.
 */
private fun getTaskSuffix(konanTarget: KonanTarget): String = when (konanTarget) {
    // iOS
    KonanTarget.IOS_ARM64 -> "IosArm64"
    KonanTarget.IOS_SIMULATOR_ARM64 -> "IosSimulatorArm64"
    KonanTarget.IOS_X64 -> "IosX64"
    // macOS
    KonanTarget.MACOS_ARM64 -> "MacosArm64"
    KonanTarget.MACOS_X64 -> "MacosX64"
    // tvOS
    KonanTarget.TVOS_ARM64 -> "TvosArm64"
    KonanTarget.TVOS_SIMULATOR_ARM64 -> "TvosSimulatorArm64"
    KonanTarget.TVOS_X64 -> "TvosX64"
    // watchOS
    KonanTarget.WATCHOS_ARM64 -> "WatchosArm64"
    KonanTarget.WATCHOS_SIMULATOR_ARM64 -> "WatchosSimulatorArm64"
    KonanTarget.WATCHOS_X64 -> "WatchosX64"
    KonanTarget.WATCHOS_DEVICE_ARM64 -> "WatchosDeviceArm64"
    else -> error("Unexpected target: $konanTarget")
}

/**
 * Determines the Swift build configuration to use.
 *
 * Priority:
 * 1. Explicit `-PswiftConfiguration=debug|release` property
 * 2. Xcode's CONFIGURATION environment variable
 * 3. `release` if any publishing tasks are being run
 * 4. `debug` otherwise, for incremental builds during local development
 */
private fun Project.getSwiftConfiguration(): String {
    val override = findProperty("swiftConfiguration")?.toString()
    if (override != null) {
        require(override in listOf("debug", "release")) {
            "swiftConfiguration must be 'debug' or 'release', got: $override"
        }
        return override
    }

    val xcodeConfig = providers.environmentVariable("CONFIGURATION").orNull
    if (xcodeConfig != null) {
        return when (xcodeConfig.lowercase()) {
            "debug" -> "debug"
            "release" -> "release"
            else -> "release"
        }
    }

    val taskNames = gradle.startParameter.taskNames
    val isPublishing = taskNames.any { it.contains("publish", ignoreCase = true) }
    if (isPublishing) return "release"

    return "debug"
}

private fun Project.getToolchainPath(): Provider<String?> =
    providers.exec {
        commandLine("xcrun", "--find", "swift")
    }.standardOutput.asText.map { swiftPath ->
        // swift is at .../Toolchains/XcodeDefault.xctoolchain/usr/bin/swift
        // but we need .../Toolchains/XcodeDefault.xctoolchain
        File(swiftPath.trim()).parentFile.parentFile.absolutePath
    }

private fun Project.getSdkPath(konanTarget: KonanTarget): String =
    getSdkPath(sdk = konanTarget.getSdkName())

private fun Project.getSdkPath(sdk: String): String =
    providers.exec {
        commandLine("xcrun", "--sdk", sdk, "--show-sdk-path")
    }.standardOutput.asText.get().trim()

private fun KonanTarget.getSdkName(): String = when (this) {
    // iOS
    KonanTarget.IOS_X64, KonanTarget.IOS_SIMULATOR_ARM64 -> "iphonesimulator"
    KonanTarget.IOS_ARM64 -> "iphoneos"
    // macOS
    KonanTarget.MACOS_ARM64, KonanTarget.MACOS_X64 -> "macosx"
    // tvOS
    KonanTarget.TVOS_X64, KonanTarget.TVOS_SIMULATOR_ARM64 -> "appletvsimulator"
    KonanTarget.TVOS_ARM64 -> "appletvos"
    // watchOS
    KonanTarget.WATCHOS_X64, KonanTarget.WATCHOS_SIMULATOR_ARM64 -> "watchsimulator"
    KonanTarget.WATCHOS_ARM64, KonanTarget.WATCHOS_DEVICE_ARM64 -> "watchos"
    else -> error("Unsupported target: ${this}")
}

private fun KonanTarget.getTriple(): String = when (this) {
    // iOS
    KonanTarget.IOS_X64 -> "x86_64-apple-ios-simulator"
    KonanTarget.IOS_SIMULATOR_ARM64 -> "arm64-apple-ios-simulator"
    KonanTarget.IOS_ARM64 -> "arm64-apple-ios"
    // macOS
    KonanTarget.MACOS_X64 -> "x86_64-apple-macosx"
    KonanTarget.MACOS_ARM64 -> "arm64-apple-macosx"
    // tvOS
    KonanTarget.TVOS_X64 -> "x86_64-apple-tvos-simulator"
    KonanTarget.TVOS_SIMULATOR_ARM64 -> "arm64-apple-tvos-simulator"
    KonanTarget.TVOS_ARM64 -> "arm64-apple-tvos"
    // watchOS
    KonanTarget.WATCHOS_X64 -> "x86_64-apple-watchos-simulator"
    KonanTarget.WATCHOS_SIMULATOR_ARM64 -> "arm64-apple-watchos-simulator"
    KonanTarget.WATCHOS_ARM64, KonanTarget.WATCHOS_DEVICE_ARM64 -> "arm64-apple-watchos"
    else -> error("Unsupported target: $this")
}
