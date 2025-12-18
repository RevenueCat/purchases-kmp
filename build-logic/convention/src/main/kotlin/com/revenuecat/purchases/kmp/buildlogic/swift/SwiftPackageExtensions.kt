package com.revenuecat.purchases.kmp.buildlogic.swift

import org.gradle.api.Project
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.io.File

/**
 * This is used to collect all Swift package dependencies declared via [swiftPackage].
 */
open class SwiftPackageRegistry {
    internal val packages = mutableListOf<SwiftDependency>()

    internal fun add(dependency: SwiftDependency) {
        packages.add(dependency)
    }
}

/**
 * Adds a Swift package dependency to the project.
 *
 * This function can be called from any Kotlin source set's dependencies block,
 * but is typically used in iOS source sets (e.g., `iosMain`).
 *
 * Usage:
 * ```
 * kotlin {
 *     sourceSets {
 *         iosMain.dependencies {
 *             swiftPackage(
 *                 path = rootProject.file("upstream/purchases-ios"),
 *                 target = "RevenueCat",
 *                 packageName = "com.revenuecat.purchases"
 *             )
 *         }
 *     }
 * }
 * ```
 *
 * @param path Path to the Swift package directory (containing `Package.swift`)
 *             or to the `Package.swift` file itself.
 * @param target The Swift target name to build and generate bindings for.
 * @param packageName The Kotlin package name for the generated cinterop bindings.
 * @param customDeclarations Optional declarations to include in the .def file.
 *                           Use this to force cinterop binding generation for types not otherwise
 *                           referenced in the public API of the Swift [target].
 */
fun KotlinDependencyHandler.swiftPackage(
    path: File,
    target: String,
    packageName: String,
    customDeclarations: String? = null
) {
    val registry = project.extensions.findByType(SwiftPackageRegistry::class.java)
        ?: error(
            "SwiftPackageRegistry not found. Make sure the `revenuecat-library` plugin is " +
                    "applied before using `swiftPackage()`."
        )
    
    val sourceSetName = getSourceSetName()
    registry.add(
        SwiftDependency(
            packagePath = path,
            target = target,
            packageName = packageName,
            sourceSetName = sourceSetName,
            customDeclarations = customDeclarations
        )
    )
}

private fun KotlinDependencyHandler.getSourceSetName(): String {
    var current: Class<*>? = this.javaClass
    while (current != null && current != Any::class.java) {
        for (field in current.declaredFields) {
            try {
                field.isAccessible = true
                val value = field.get(this)
                if (value is KotlinSourceSet) {
                    return value.name
                }
            } catch (_: Exception) {
                // Continue to next field
            }
        }
        current = current.superclass
    }

    error(
        "Could not determine source set for swiftPackage(). " +
        "This might be due to a Kotlin Gradle Plugin version incompatibility. "
    )
}

internal fun Project.getOrCreateSwiftPackageRegistry(): SwiftPackageRegistry {
    return extensions.findByType(SwiftPackageRegistry::class.java)
        ?: extensions.create("swiftPackageRegistry", SwiftPackageRegistry::class.java)
}
