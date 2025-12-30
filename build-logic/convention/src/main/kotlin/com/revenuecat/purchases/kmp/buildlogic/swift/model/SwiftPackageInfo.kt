package com.revenuecat.purchases.kmp.buildlogic.swift.model

import groovy.json.JsonSlurper
import org.gradle.api.Project
import java.io.File
import java.io.Serializable


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
class SwiftPackageInfo(
    private val targets: List<TargetInfo>,
    /** Platform deployment targets. */
    val platformVersions: Map<String, String>,
) {
    data class TargetInfo(
        val name: String,
        val path: String?,
        val targetDependencies: List<String>,
        val resources: List<ResourceInfo>
    )

    data class ResourceInfo(
        val path: String,
        val localization: String?
    ) : Serializable

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

    /**
     * Get the list of resources for a given target.
     */
    fun getTargetResources(targetName: String): List<ResourceInfo> {
        val target = targets.find { it.name == targetName }
            ?: error("Target '$targetName' not found in Package.swift. Available targets: ${targets.map { it.name }}")
        return target.resources
    }

    companion object {

        /**
         * Parses output from `swift package describe --type json` into a [SwiftPackageInfo].
         */
        @Suppress("UNCHECKED_CAST")
        fun parse(json: String): SwiftPackageInfo {
            val parsed = JsonSlurper().parseText(json) as Map<String, Any>
            
            // platforms
            val platformsJson = parsed["platforms"] as? List<Map<String, Any>> ?: emptyList()
            val platforms = platformsJson.associate { platform ->
                val name = platform["name"] as String
                val version = platform["version"] as String
                name to version
            }

            // targets
            val targetsJson = parsed["targets"] as? List<Map<String, Any>> ?: emptyList()
            val targets = targetsJson.map { targetJson ->
                val dependencies = targetJson["target_dependencies"] as? List<String> ?: emptyList()
                
                // resources
                val resourcesJson = targetJson["resources"] as? List<Map<String, Any>> ?: emptyList()
                val resources = resourcesJson.map { resourceJson ->
                    val path = resourceJson["path"] as String
                    val rule = resourceJson["rule"] as Map<String, Any>
                    
                    val localization = when {
                        rule.containsKey("process") -> {
                            val processRule = rule["process"] as? Map<String, Any>
                            processRule?.get("localization") as? String
                        }
                        else -> null
                    }
                    
                    ResourceInfo(path = path, localization = localization)
                }

                TargetInfo(
                    name = targetJson["name"] as String,
                    path = targetJson["path"] as? String,
                    targetDependencies = dependencies,
                    resources = resources
                )
            }

            return SwiftPackageInfo(targets, platforms)
        }
    }
}
