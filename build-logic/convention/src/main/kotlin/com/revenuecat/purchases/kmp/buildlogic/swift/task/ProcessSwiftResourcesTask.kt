package com.revenuecat.purchases.kmp.buildlogic.swift.task

import com.revenuecat.purchases.kmp.buildlogic.swift.model.SwiftPackageInfo.ResourceInfo
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import java.io.File
import javax.inject.Inject

/**
 * Processes Swift package resources to include in the .klib via Compose Resources.
 */
abstract class ProcessSwiftResourcesTask @Inject constructor(
    private val execOperations: ExecOperations
) : DefaultTask() {

    /**
     * Platform deployment targets from Package.swift.
     */
    @get:Input
    abstract val platformVersions: MapProperty<String, String>

    /**
     * Metadata of the resources to process.
     */
    @get:Input
    abstract val resources: ListProperty<ResourceInfo>

    /**
     * The actual resource files. Only tracked for incremental builds.
     */
    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val resourceFiles: ConfigurableFileCollection

    /**
     * The Kotlin source set name (e.g. "iosMain").
     */
    @get:Input
    abstract val sourceSetName: Property<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun processResources() {
        val output = outputDir.get().asFile
        output.deleteRecursively()
        val filesDir = output.resolve("files").also { it.mkdirs() }

        val resourceList = resources.get()
        if (resourceList.isEmpty()) {
            logger.lifecycle("No resources to process")
            return
        }

        val xcassets = mutableListOf<File>()
        val copyFiles = mutableListOf<File>()
        val localizedStrings = mutableMapOf<String, MutableList<File>>()

        for (resource in resourceList) {
            val file = File(resource.path)
            when {
                resource.path.endsWith(".xcassets") -> xcassets.add(file)
                resource.localization != null -> {
                    localizedStrings.getOrPut(resource.localization) { mutableListOf() }.add(file)
                }
                else -> copyFiles.add(file)
            }
        }

        // Plain files
        copyFiles.filter { it.exists() }.forEach { file ->
            file.copyTo(filesDir.resolve(file.name), overwrite = true)
        }

        // xcassets
        xcassets.filter { it.exists() && it.isDirectory }.forEach { xcasset ->
            compileXcassets(xcasset, filesDir)
        }

        // localized resources
        localizedStrings.values.flatten().filter { it.exists() }.forEach { file ->
            val destDir = filesDir.resolve(file.parentFile.name).also { it.mkdirs() }
            file.copyTo(destDir.resolve(file.name), overwrite = true)
        }
    }

    private fun compileXcassets(xcasset: File, outputDir: File) {
        val sourceSet = sourceSetName.get()
        
        val actoolPlatform = when {
            sourceSet.startsWith("ios") -> "iphoneos"
            sourceSet.startsWith("macos") -> "macosx"
            sourceSet.startsWith("tvos") -> "appletvos"
            sourceSet.startsWith("watchos") -> "watchos"
            else -> error("Cannot determine actool platform from source set: $sourceSet")
        }
        
        val spmPlatform = when {
            sourceSet.startsWith("ios") -> "ios"
            sourceSet.startsWith("macos") -> "macos"
            sourceSet.startsWith("tvos") -> "tvos"
            sourceSet.startsWith("watchos") -> "watchos"
            else -> error("Cannot determine platform from source set: $sourceSet")
        }
        
        val deploymentTarget = platformVersions.get()[spmPlatform]
            ?: error("Platform '$spmPlatform' not found in Package.swift platforms: ${platformVersions.get().keys}")

        execOperations.exec {
            commandLine(
                "xcrun", "actool",
                xcasset.absolutePath,
                "--compile", outputDir.absolutePath,
                "--platform", actoolPlatform,
                "--minimum-deployment-target", deploymentTarget,
                "--output-format", "human-readable-text"
            )
        }
    }
}
