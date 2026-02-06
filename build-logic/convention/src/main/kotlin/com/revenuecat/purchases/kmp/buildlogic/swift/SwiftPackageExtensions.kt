package com.revenuecat.purchases.kmp.buildlogic.swift

import com.revenuecat.purchases.kmp.buildlogic.swift.model.SwiftDependency
import com.revenuecat.purchases.kmp.buildlogic.swift.model.SwiftSettings
import org.gradle.api.Project
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
 * This function should be called in a dependencies block of a Kotlin source set belonging to an
 * Apple target, such as `iosMain`.
 *
 * @param path Path to the Swift package directory (containing `Package.swift`) or to the
 * `Package.swift` file itself.
 * @param target The Swift target name to build and generate bindings for.
 * @param packageName The Kotlin package name for the generated cinterop bindings.
 * @param customDeclarations Optional declarations to include in the .def file. Use this to force
 * cinterop binding generation for types not otherwise referenced in the public API of the Swift
 * [target].
 * @param swiftSettings Swift compiler settings. Use [SwiftSettings] DSL:
 * ```kotlin
 * swiftSettings = SwiftSettings {
 *     define("MY_FLAG")
 * }
 * ```
 */
fun KotlinDependencyHandler.swiftPackage(
    path: File,
    target: String,
    packageName: String,
    customDeclarations: String? = null,
    swiftSettings: SwiftSettings? = null,
) {
    val registry = project.extensions.findByType(SwiftPackageRegistry::class.java)
        ?: error(
            "SwiftPackageRegistry not found. Make sure the `revenuecat-library` plugin is " +
                    "applied before using `swiftPackage()`."
        )
    
    val sourceSetName = getSourceSetName()
    val dependency = SwiftDependency(
        packagePath = path,
        target = target,
        packageName = packageName,
        sourceSetName = sourceSetName,
        customDeclarations = customDeclarations,
        swiftSettings = swiftSettings,
    )
    registry.add(dependency)
    
    // Also register to the global registry for cross-project dependency detection
    val globalRegistry = project.getOrCreateGlobalSwiftRegistry()
    globalRegistry.register(target, project, dependency)
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
