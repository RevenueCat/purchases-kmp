package com.revenuecat.purchases.kmp.buildlogic.swift.model

import java.io.File

/**
 * Configuration for a Swift dependency.
 *
 * @param packagePath Path to either the Package.swift file or the directory containing it
 * @param target The Swift target name (e.g., "RevenueCat" or "RevenueCatUI")
 * @param packageName The Kotlin package name for the generated cinterop bindings
 * @param sourceSetName The Kotlin source set this dependency was declared in (e.g., "iosMain")
 * @param customDeclarations Optional declarations to force cinterop binding generation
 * @param swiftSettings Swift compiler settings (e.g. defines)
 */
internal class SwiftDependency(
    packagePath: File,
    val target: String,
    val packageName: String,
    val sourceSetName: String,
    val customDeclarations: String? = null,
    val swiftSettings: SwiftSettings? = null,
) {
    /**
     * Directory containing the Package.swift file (used as working directory for swift build).
     * Resolved from [packagePath] - if it points to a Package.swift file, uses its parent directory.
     */
    val packageDir: File = if (packagePath.name == "Package.swift") {
        packagePath.parentFile
    } else {
        packagePath
    }

    // Derived properties computed from the target name
    val headerName: String get() = "$target-Swift.h"
    val libraryName: String get() = "lib$target.a"
    val moduleName: String get() = target
}
