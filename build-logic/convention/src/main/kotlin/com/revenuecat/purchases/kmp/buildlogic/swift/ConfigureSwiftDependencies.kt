package com.revenuecat.purchases.kmp.buildlogic.swift

import groovy.json.JsonSlurper
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.withType
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

    // Group dependencies by packageDir to avoid running `swift package describe` multiple times
    val dependenciesByPackageDir = dependencies.groupBy { it.packageDir }

    // We need to find the source directory of each Swift target.
    val targetSourceDirs = mutableMapOf<SwiftDependency, File>()
    dependenciesByPackageDir.forEach { (packageDir, deps) ->
        val packageInfo = getSwiftPackageInfo(packageDir)
        deps.forEach { dep ->
            targetSourceDirs[dep] = packageInfo.getTargetSourceDir(dep.target, packageDir)
        }
    }

    dependencies.forEach { dependency ->
        val targetSourceDir = targetSourceDirs[dependency]
            ?: error("Could not resolve source directory for target ${dependency.target}")
        configureSwiftDependency(kotlin, dependency, targetSourceDir)
    }
}

private fun Project.configureSwiftDependency(
    kotlin: KotlinMultiplatformExtension,
    dependency: SwiftDependency,
    targetSourceDir: File
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
        this.defFile.set(defFile)
    }

    kotlin.targets.withType<KotlinNativeTarget> {
        // Skip this target if it doesn't include the source set this dependency is added to.
        if (!includesSourceSet(dependency.sourceSetName)) {
            return@withType
        }

        if (!konanTarget.isAppleTarget()) {
            error(
                "swiftPackage() was called from source set '${dependency.sourceSetName}', " +
                        "but only Apple targets are supported."
            )
        }

        val mainCompilation = compilations.getByName("main")
        val swiftBuildTask = registerSwiftBuildTask(this, dependency, targetSourceDir)
        val swiftOutputDir = layout.buildDirectory
            .dir("swift-packages/${dependency.target}/${konanTarget.name}")
            .get().asFile

        val sdkPath = getSdkPath(konanTarget)

        mainCompilation.cinterops.create(dependency.target) {
            defFile(defFile)
            extraOpts("-libraryPath", swiftOutputDir.absolutePath)

            compilerOpts(
                "-fmodules",
                "-I", swiftOutputDir.absolutePath,
                "-isysroot", sdkPath
            )

            // Make sure we generate the .def file and compile Swift before cinterop runs.
            tasks.named(interopProcessingTaskName).configure {
                dependsOn(generateDefTask)
                dependsOn(swiftBuildTask)
                // Rerun cinterop if the .def file or header changes.
                inputs.file(swiftBuildTask.flatMap { it.outputDir.file(it.headerName.get()) })
                inputs.file(defFile)
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

    // Direct match
    if (sourceSetName == "${targetName}Main") {
        return true
    }

    // Check hierarchical source sets based on naming conventions
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
    val taskSuffix = when (kotlinTarget.konanTarget) {
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
        else -> error("Unexpected target: ${kotlinTarget.konanTarget}")
    }
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
        scratchDir.set(layout.buildDirectory.dir("swift-packages/.build"))
    }
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
    if (isPublishing) {
        return "release"
    }

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

/**
 * Runs `swift package describe --type json` and parses the output.
 */
private fun Project.getSwiftPackageInfo(packageDir: File): SwiftPackageInfo {
    val output = providers.exec {
        workingDir = packageDir
        commandLine("swift", "package", "describe", "--type", "json")
    }.standardOutput.asText.get()
    return SwiftPackageInfo.parse(output)
}

/**
 * Parsed information from `swift package describe --type json`.
 */
private class SwiftPackageInfo(
    private val targets: List<TargetInfo>
) {
    data class TargetInfo(
        val name: String,
        val path: String?
    )

    fun getTargetSourceDir(targetName: String, packageDir: File): File {
        val target = targets.find { it.name == targetName }
            ?: error("Target '$targetName' not found in Package.swift. Available targets: ${targets.map { it.name }}")

        val relativePath = target.path ?: "Sources/$targetName"
        return packageDir.resolve(relativePath)
    }

    companion object {

        /**
         * Parses output from `swift package describe --type json` into a [SwiftPackageInfo].
         */
        @Suppress("UNCHECKED_CAST")
        fun parse(json: String): SwiftPackageInfo {
            val parsed = JsonSlurper().parseText(json) as Map<String, Any>
            val targetsJson = parsed["targets"] as? List<Map<String, Any>> ?: emptyList()

            val targets = targetsJson.map { targetJson ->
                TargetInfo(
                    name = targetJson["name"] as String,
                    path = targetJson["path"] as? String
                )
            }

            return SwiftPackageInfo(targets)
        }
    }
}
