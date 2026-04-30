package com.revenuecat.purchases.kmp.buildlogic.test

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.intellij.lang.annotations.Language
import java.io.File
import kotlin.io.path.createTempDirectory


/**
 * Run an end-to-end test of the RevenueCatLibraryConvention plugin.
 */
fun revenueCatLibraryPluginTest(
    test: RevenueCatLibraryPluginTestContext.() -> Unit
) {
    val projectDir = createTempDirectory("gradle-test").toFile()
    try {
        setupBaseProject(projectDir)
        RevenueCatLibraryPluginTestContext(projectDir).test()
    } finally {
        projectDir.deleteRecursively()
    }
}


/**
 * Entry-point for our test DSL.
 */
class RevenueCatLibraryPluginTestContext(
    val projectDir: File
) {
    private val swiftPackages = mutableMapOf<String, SwiftPackageHandle>()
    private val swiftPackageConfigs = mutableListOf<SwiftPackageConfig>()
    private val subprojects = mutableMapOf<String, SubprojectContext>()
    private var buildFilesWritten = false

    fun addSwiftPackage(
        kotlinSourceSet: String,
        relativePath: String = "swift-package",
        targetName: String = "TestModule",
        kotlinPackageName: String = "test.swift",
        configure: SwiftPackageBuilder.() -> Unit
    ): SwiftPackageHandle {
        val packageDir = projectDir.resolve(relativePath)
        packageDir.mkdirs()

        val builder = SwiftPackageBuilder(packageDir, targetName)
        builder.configure()
        val handle = builder.build()

        swiftPackages[relativePath] = handle
        swiftPackageConfigs.add(
            SwiftPackageConfig(
                relativePath = relativePath,
                targetName = targetName,
                kotlinPackageName = kotlinPackageName,
                kotlinSourceSet = kotlinSourceSet,
                hasResources = handle.hasResources
            )
        )

        return handle
    }

    /**
     * Creates a Swift package with multiple targets (and optional dependencies between them) in
     * [relativePath] under [projectDir]. This is used by cross-project tests where a single
     * shared `Package.swift` is referenced from multiple Gradle subprojects.
     */
    fun addMultiTargetSwiftPackage(
        relativePath: String = "swift-package",
        configure: MultiTargetPackageBuilder.() -> Unit
    ): MultiTargetPackageHandle {
        val packageDir = projectDir.resolve(relativePath).also { it.mkdirs() }
        val builder = MultiTargetPackageBuilder(packageDir)
        builder.configure()
        return builder.build()
    }

    /**
     * Adds (or returns) a Gradle subproject named [name]. Use [SubprojectContext.useSwiftPackage]
     * to register a Swift package target within the subproject.
     */
    fun addSubproject(name: String): SubprojectContext =
        subprojects.getOrPut(name) {
            SubprojectContext(name = name, dir = projectDir.resolve(name))
        }

    fun runBuild(vararg tasks: String): BuildResult {
        if (!buildFilesWritten) {
            writeBuildFiles()
            buildFilesWritten = true
        }
        return GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments(*tasks)
            .withPluginClasspath()
            .forwardOutput()
            .build()
    }

    private fun writeBuildFiles() {
        if (subprojects.isNotEmpty()) {
            appendSubprojectsToSettings()
        }
        writeRootBuildFile()
        for (subproject in subprojects.values) {
            writeSubprojectBuildFile(subproject)
        }
    }

    private fun appendSubprojectsToSettings() {
        val settingsFile = projectDir.resolve("settings.gradle.kts")
        val includes = subprojects.keys.joinToString("\n") { """include(":$it")""" }
        settingsFile.appendText("\n\n$includes\n")
    }

    private fun writeRootBuildFile() {
        val bySourceSet = swiftPackageConfigs.groupBy { it.kotlinSourceSet }

        val sourceSetBlocks = bySourceSet.entries.joinToString("\n\n                ") { (sourceSet, configs) ->
            val swiftPackageCalls = configs.joinToString("\n                        ") { config ->
                """swiftPackage(
                            path = file("${config.relativePath}"),
                            target = "${config.targetName}",
                            packageName = "${config.kotlinPackageName}"
                        )"""
            }
            """$sourceSet {
                    dependencies {
                        $swiftPackageCalls
                    }
                }
                """
        }

        val needsCompose = swiftPackageConfigs.any { it.hasResources }
        val composePlugin = if (needsCompose) {
            """
                id("org.jetbrains.compose")
                id("org.jetbrains.kotlin.plugin.compose")"""
        } else ""

        // When the root project has no swiftPackages but does have subprojects, we still need a
        // root build file so the test project is well-formed; in that case we keep it empty.
        if (swiftPackageConfigs.isEmpty() && subprojects.isNotEmpty()) {
            projectDir.resolve("build.gradle.kts").writeText("")
            return
        }

        projectDir.resolve("build.gradle.kts").writeText(
            // language=kotlin
            """
            import com.revenuecat.purchases.kmp.buildlogic.swift.swiftPackage
            
            plugins {
                id("revenuecat-library")$composePlugin
            }
            
            kotlin {
                iosSimulatorArm64()
                
                sourceSets {
                    $sourceSetBlocks
                }
            }
            
            android {
                namespace = "com.test.module"
            }
            """.trimIndent()
        )
    }

    private fun writeSubprojectBuildFile(subproject: SubprojectContext) {
        subproject.dir.mkdirs()

        val sourceSetBlocks = subproject.swiftPackageConfigs
            .groupBy { it.kotlinSourceSet }
            .entries
            .joinToString("\n\n                ") { (sourceSet, configs) ->
                val swiftPackageCalls = configs.joinToString("\n                        ") { config ->
                    """swiftPackage(
                            path = rootProject.file("${config.packageRelPath}"),
                            target = "${config.targetName}",
                            packageName = "${config.kotlinPackageName}"
                        )"""
                }
                """$sourceSet {
                    dependencies {
                        $swiftPackageCalls
                    }
                }
                """
            }

        val evaluationDependsOnLines = subproject.evaluationDependencies
            .distinct()
            .joinToString("\n") { """evaluationDependsOn(":$it")""" }

        subproject.dir.resolve("build.gradle.kts").writeText(
            // language=kotlin
            """
            import com.revenuecat.purchases.kmp.buildlogic.swift.swiftPackage
            
            plugins {
                id("revenuecat-library")
            }
            
            $evaluationDependsOnLines
            
            kotlin {
                iosSimulatorArm64()
                
                sourceSets {
                    $sourceSetBlocks
                }
            }
            
            android {
                namespace = "com.test.${subproject.name}"
            }
            """.trimIndent()
        )
    }
}

/**
 * Represents a single Gradle subproject in the test setup. Each subproject gets its own
 * `build.gradle.kts` that applies the `revenuecat-library` plugin and registers Swift package
 * targets via [useSwiftPackage].
 */
class SubprojectContext internal constructor(
    val name: String,
    val dir: File,
) {
    internal val swiftPackageConfigs = mutableListOf<SubprojectSwiftPackageConfig>()
    internal val evaluationDependencies = mutableListOf<String>()

    /**
     * Registers a `swiftPackage()` call in this subproject's `build.gradle.kts` pointing to an
     * existing Swift package directory (typically created by
     * [RevenueCatLibraryPluginTestContext.addMultiTargetSwiftPackage]).
     *
     * @param dependsOnSubprojects names of other subprojects that must be evaluated before this
     *   one. Useful when this subproject's swift package target depends on a target registered
     *   in another subproject: the cross-project wiring done in `afterEvaluate` only finds the
     *   dependency in the global registry if the dep subproject was evaluated first. Each name
     *   becomes an `evaluationDependsOn(":...")` call at the top of the subproject's build file.
     */
    fun useSwiftPackage(
        kotlinSourceSet: String,
        packageDir: File,
        targetName: String,
        kotlinPackageName: String = "test.swift",
        dependsOnSubprojects: List<String> = emptyList(),
    ): SwiftPackageHandle {
        // Path passed to `rootProject.file(...)` – relative to the project root.
        val packageRelPath = packageDir.relativeTo(dir.parentFile).path

        swiftPackageConfigs.add(
            SubprojectSwiftPackageConfig(
                packageRelPath = packageRelPath,
                targetName = targetName,
                kotlinPackageName = kotlinPackageName,
                kotlinSourceSet = kotlinSourceSet,
            )
        )
        evaluationDependencies.addAll(dependsOnSubprojects)

        val sourcesDir = packageDir.resolve("Sources/$targetName")
        return SwiftPackageHandle(
            target = SwiftTargetHandle(
                sourcesDir = sourcesDir,
                name = targetName,
                resources = SwiftResourcesHandle(packageDir.resolve("Sources/$targetName/Resources"))
            ),
            hasResources = false,
            gradlePath = ":$name",
        )
    }
}

internal data class SubprojectSwiftPackageConfig(
    val packageRelPath: String,
    val targetName: String,
    val kotlinPackageName: String,
    val kotlinSourceSet: String,
)

class SwiftTargetHandle(
    private val sourcesDir: File,
    val name: String,
    val resources: SwiftResourcesHandle
) {

    fun readSourceFile(relativePath: String): String = sourceFile(relativePath).readText()

    fun writeSourceFile(relativePath: String, @Language("swift") contents: String) {
        sourceFile(relativePath).apply {
            parentFile.mkdirs()
            writeText(contents)
        }
    }

    fun deleteSourceFile(relativePath: String) {
        sourceFile(relativePath).delete()
    }

    private fun sourceFile(relativePath: String): File = sourcesDir.resolve(relativePath)
}

class SwiftResourcesHandle(private val resourcesDir: File) {

    fun writeResourceFile(relativePath: String, contents: String) {
        resourceFile(relativePath).apply {
            parentFile.mkdirs()
            writeText(contents)
        }
    }

    fun deleteResourceFile(relativePath: String) {
        resourceFile(relativePath).delete()
    }

    fun renameResourceFile(oldRelativePath: String, newRelativePath: String) {
        val oldFile = resourceFile(oldRelativePath)
        val newFile = resourceFile(newRelativePath)
        newFile.parentFile.mkdirs()
        oldFile.renameTo(newFile)
    }

    private fun resourceFile(relativePath: String): File = resourcesDir.resolve(relativePath)
}

class SwiftPackageBuilder(
    private val packageDir: File,
    private val targetName: String
) {
    private val sourcesDir = packageDir.resolve("Sources/$targetName").also { it.mkdirs() }
    private val resourcesDir = packageDir.resolve("Sources/$targetName/Resources")
    private val resourceFiles = mutableListOf<String>()
    private var defaultLocale: String? = null

    fun writeSourceFile(relativePath: String, @Language("swift") contents: String) {
        sourcesDir.resolve(relativePath).apply {
            parentFile.mkdirs()
            writeText(contents)
        }
    }

    fun writeResourceFile(relativePath: String, contents: String) {
        resourcesDir.mkdirs()
        resourcesDir.resolve(relativePath).apply {
            parentFile.mkdirs()
            writeText(contents)
        }
        if (!resourceFiles.contains(relativePath)) {
            resourceFiles.add(relativePath)
        }
    }

    fun writeLocalizedStrings(locale: String, contents: String) {
        resourcesDir.mkdirs()
        val localeDir = resourcesDir.resolve("$locale.lproj")
        localeDir.mkdirs()
        localeDir.resolve("Localizable.strings").writeText(contents)
        val relativePath = "$locale.lproj/Localizable.strings"
        if (!resourceFiles.contains(relativePath)) {
            resourceFiles.add(relativePath)
        }
        // Set the first locale as the default localization
        if (defaultLocale == null) {
            defaultLocale = locale
        }
    }

    fun writeAssetCatalog(name: String, configure: AssetCatalogBuilder.() -> Unit) {
        resourcesDir.mkdirs()
        val xcassetsDir = resourcesDir.resolve(name)
        xcassetsDir.mkdirs()
        // Write root Contents.json
        xcassetsDir.resolve("Contents.json").writeText(
            """{"info":{"author":"xcode","version":1}}"""
        )
        AssetCatalogBuilder(xcassetsDir).configure()
        if (!resourceFiles.contains(name)) {
            resourceFiles.add(name)
        }
    }

    internal fun build(): SwiftPackageHandle {
        val hasResources = resourceFiles.isNotEmpty()
        
        val resourcesDecl = if (hasResources) {
            ", resources: [.process(\"Resources\")]"
        } else ""
        
        val defaultLocalizationDecl = if (defaultLocale != null) {
            "\n    defaultLocalization: \"$defaultLocale\","
        } else ""

        // Write Package.swift
        packageDir.resolve("Package.swift").writeText(
            // language=swift
            """
            // swift-tools-version:5.9
            import PackageDescription
            
            let package = Package(
                name: "${packageDir.name}",$defaultLocalizationDecl
                platforms: [.iOS(.v14)],
                products: [
                    .library(name: "$targetName", targets: ["$targetName"])
                ],
                targets: [
                    .target(name: "$targetName", path: "Sources/$targetName"$resourcesDecl)
                ]
            )
            """.trimIndent()
        )

        return SwiftPackageHandle(
            target = SwiftTargetHandle(
                sourcesDir = sourcesDir,
                name = targetName,
                resources = SwiftResourcesHandle(resourcesDir)
            ),
            hasResources = hasResources
        )
    }
}

class AssetCatalogBuilder(private val xcassetsDir: File) {
    fun addImageSet(name: String, @Language("svg") svgContent: String) {
        val imagesetDir = xcassetsDir.resolve("$name.imageset")
        imagesetDir.mkdirs()
        imagesetDir.resolve("Contents.json").writeText(
            """{"images":[{"filename":"$name.svg","idiom":"universal"}],"info":{"author":"xcode","version":1}}"""
        )
        imagesetDir.resolve("$name.svg").writeText(svgContent)
    }
}

class SwiftPackageHandle(
    val target: SwiftTargetHandle,
    internal val hasResources: Boolean,
    /** Gradle path prefix for the project that owns this package (e.g. ":consumer"). Empty for root. */
    internal val gradlePath: String = ""
) {
    val cinteropTaskName: String get() = "$gradlePath:cinterop${target.name}IosSimulatorArm64"
    val processResourcesTaskName: String get() = "$gradlePath:processSwiftResources${target.name}"
    val compileSwiftTaskName: String get() = "$gradlePath:compileSwift${target.name}IosSimulatorArm64"

    /**
     * Returns the output directory where the Swift build artifacts (header, library, modulemap)
     * are placed for the iosSimulatorArm64 target.
     */
    fun getSwiftOutputDir(projectDir: File): File {
        val moduleDir = if (gradlePath.isEmpty()) projectDir else projectDir.resolve(gradlePath.removePrefix(":"))
        return moduleDir.resolve("build/swift-packages/${target.name}/ios_simulator_arm64")
    }

    /**
     * Returns the scratch directory used by `swift build` for this target.
     */
    fun getScratchDir(projectDir: File): File {
        val moduleDir = if (gradlePath.isEmpty()) projectDir else projectDir.resolve(gradlePath.removePrefix(":"))
        return moduleDir.resolve("build/swift-packages/${target.name}/.build")
    }

    /**
     * Returns the output directory where processed resources are placed.
     */
    fun getResourceOutputDir(projectDir: File): File {
        val moduleDir = if (gradlePath.isEmpty()) projectDir else projectDir.resolve(gradlePath.removePrefix(":"))
        return moduleDir.resolve("build/swift-resources/${target.name}/files")
    }
}

/**
 * Builds a Swift package with multiple targets, optionally with target-to-target dependencies.
 * The generated `Package.swift` declares each target as both a library product and a target.
 */
class MultiTargetPackageBuilder internal constructor(
    private val packageDir: File
) {
    private val targetSpecs = mutableListOf<MultiTargetSpec>()

    /**
     * Declares a target named [name] in this package. [dependencies] are names of other targets
     * (declared in the same package) that this target depends on.
     */
    fun target(
        name: String,
        dependencies: List<String> = emptyList(),
        configure: SwiftSourcesBuilder.() -> Unit,
    ) {
        val sourcesDir = packageDir.resolve("Sources/$name").also { it.mkdirs() }
        SwiftSourcesBuilder(sourcesDir).apply(configure)
        targetSpecs.add(MultiTargetSpec(name = name, dependencies = dependencies, sourcesDir = sourcesDir))
    }

    internal fun build(): MultiTargetPackageHandle {
        require(targetSpecs.isNotEmpty()) { "At least one target must be declared" }

        val productDecls = targetSpecs.joinToString(",\n        ") { spec ->
            """.library(name: "${spec.name}", targets: ["${spec.name}"])"""
        }
        val targetDecls = targetSpecs.joinToString(",\n        ") { spec ->
            val depsClause = if (spec.dependencies.isEmpty()) ""
            else ", dependencies: [${spec.dependencies.joinToString(", ") { "\"$it\"" }}]"
            """.target(name: "${spec.name}"$depsClause, path: "Sources/${spec.name}")"""
        }

        packageDir.resolve("Package.swift").writeText(
            // language=swift
            """
            // swift-tools-version:5.9
            import PackageDescription
            
            let package = Package(
                name: "${packageDir.name}",
                platforms: [.iOS(.v14)],
                products: [
                    $productDecls
                ],
                targets: [
                    $targetDecls
                ]
            )
            """.trimIndent()
        )

        val targetHandles = targetSpecs.associate { spec ->
            spec.name to SwiftTargetHandle(
                sourcesDir = spec.sourcesDir,
                name = spec.name,
                resources = SwiftResourcesHandle(spec.sourcesDir.resolve("Resources"))
            )
        }
        return MultiTargetPackageHandle(packageDir = packageDir, targets = targetHandles)
    }
}

/**
 * Source-only DSL builder used inside [MultiTargetPackageBuilder.target]. Mirrors the source
 * file helpers on [SwiftPackageBuilder] but doesn't write a `Package.swift` of its own (the
 * surrounding [MultiTargetPackageBuilder] does that).
 */
class SwiftSourcesBuilder internal constructor(
    private val sourcesDir: File
) {
    fun writeSourceFile(relativePath: String, @Language("swift") contents: String) {
        sourcesDir.resolve(relativePath).apply {
            parentFile.mkdirs()
            writeText(contents)
        }
    }
}

private data class MultiTargetSpec(
    val name: String,
    val dependencies: List<String>,
    val sourcesDir: File,
)

class MultiTargetPackageHandle internal constructor(
    val packageDir: File,
    val targets: Map<String, SwiftTargetHandle>,
)

private data class SwiftPackageConfig(
    val relativePath: String,
    val targetName: String,
    val kotlinPackageName: String,
    val kotlinSourceSet: String,
    val hasResources: Boolean = false
)

private fun setupBaseProject(projectDir: File) {
    val mainProjectDir = File(System.getProperty("mainProjectDir")
        ?: error("rootProjectDir system property not set."))
    val mainProjectVersionCatalog = mainProjectDir.resolve("gradle/libs.versions.toml")
    
    projectDir.resolve("gradle").apply {
        mkdirs()
        resolve("libs.versions.toml").writeText(mainProjectVersionCatalog.readText())
    }

    projectDir.resolve("settings.gradle.kts").writeText(
        // language=kotlin
        """
        pluginManagement {
            repositories {
                gradlePluginPortal()
                mavenCentral()
                google()
            }
        }
        
        dependencyResolutionManagement {
            repositories {
                mavenCentral()
                google()
            }
        }
        
        rootProject.name = "test-project"
        """.trimIndent()
    )

    val androidHome = System.getenv("ANDROID_HOME")
        ?: (System.getProperty("user.home") + "/Library/Android/sdk")
    projectDir.resolve("local.properties").writeText(
        // language=properties
        """
            sdk.dir=$androidHome
            """.trimIndent()
    )

    projectDir.resolve("gradle.properties").writeText(
        // language=properties
        """
            kotlin.mpp.enableCInteropCommonization=true
            """.trimIndent()
    )
}
