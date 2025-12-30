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
    private var buildFileWritten = false

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

    fun runBuild(vararg tasks: String): BuildResult {
        if (!buildFileWritten) {
            writeBuildFile()
            buildFileWritten = true
        }
        return GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments(*tasks)
            .withPluginClasspath()
            .forwardOutput()
            .build()
    }

    private fun writeBuildFile() {
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
}

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

    internal fun build(): SwiftPackageHandle {
        val hasResources = resourceFiles.isNotEmpty()
        
        val resourcesDecl = if (hasResources) {
            ", resources: [.process(\"Resources\")]"
        } else ""

        // Write Package.swift
        packageDir.resolve("Package.swift").writeText(
            // language=swift
            """
            // swift-tools-version:5.9
            import PackageDescription
            
            let package = Package(
                name: "${packageDir.name}",
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

class SwiftPackageHandle(
    val target: SwiftTargetHandle,
    internal val hasResources: Boolean
) {
    val cinteropTaskName: String get() = ":cinterop${target.name}IosSimulatorArm64"
    val processResourcesTaskName: String get() = ":processSwiftResources${target.name}"
}

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
