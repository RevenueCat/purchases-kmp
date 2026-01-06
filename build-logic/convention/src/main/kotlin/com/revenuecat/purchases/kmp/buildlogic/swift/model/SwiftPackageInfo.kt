package com.revenuecat.purchases.kmp.buildlogic.swift.model

import groovy.json.JsonSlurper
import org.gradle.api.Project
import java.io.File


/**
 * Runs `swift package describe --type json` and parses the output.
 */
internal fun Project.getSwiftPackageInfo(packageDir: File): SwiftPackageInfo {
    val result = providers.exec {
        workingDir = packageDir
        commandLine("xcrun", "swift", "package", "describe", "--type", "json")
        isIgnoreExitValue = true
        // Avoids trying to use the iOS SDK to parse the Package.swift when building from Xcode.
        // This environment change is scoped to this subprocess only.
        environment("SDKROOT", "")
    }

    val exitCode = result.result.get().exitValue
    if (exitCode != 0) {
        val stderr = result.standardError.asText.get()
        error("Failed to run 'swift package describe' in $packageDir (exit code $exitCode): $stderr")
    }

    val output = result.standardOutput.asText.get()
    return SwiftPackageInfo.parse(output)
}

/**
 * Parsed information from `swift package describe --type json`.
 */
internal class SwiftPackageInfo(
    private val targets: List<TargetInfo>
) {
    data class TargetInfo(
        val name: String,
        val path: String?,
        val targetDependencies: List<String>
    )

    fun getTargetSourceDir(targetName: String, packageDir: File): File {
        val target = targets.find { it.name == targetName }
            ?: error("Target '$targetName' not found in Package.swift. Available targets: ${targets.map { it.name }}")

        val relativePath = target.path ?: "Sources/$targetName"
        return packageDir.resolve(relativePath)
    }

    /**
     * Get the list of target dependencies for a given target.
     */
    fun getTargetDependencies(targetName: String): List<String> {
        val target = targets.find { it.name == targetName }
            ?: error("Target '$targetName' not found in Package.swift. Available targets: ${targets.map { it.name }}")
        return target.targetDependencies
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
                // Parse target dependencies from the JSON
                val dependencies = targetJson["target_dependencies"] as? List<String> ?: emptyList()

                TargetInfo(
                    name = targetJson["name"] as String,
                    path = targetJson["path"] as? String,
                    targetDependencies = dependencies
                )
            }

            return SwiftPackageInfo(targets)
        }
    }
}
