package com.revenuecat.purchases.kmp.buildlogic.swift.model

import java.io.Serializable

/**
 * Swift compiler settings.
 *
 * Usage:
 * ```kotlin
 * swiftPackage(
 *     path = rootProject.file("swift-package"),
 *     target = "MyTarget",
 *     packageName = "com.example.mypackage",
 *     swiftSettings = SwiftSettings {
 *         define("COMPOSE_RESOURCES")
 *     }
 * )
 * ```
 */
class SwiftSettings private constructor(
    private val defines: MutableList<String> = mutableListOf()
) : Serializable {
    
    /**
     * Adds a compiler `define` flag, equivalent to SwiftPM's `.define("FLAG")`.
     * This passes `-D FLAG` to the Swift compiler.
     */
    fun define(name: String) {
        defines.add(name)
    }
    
    /**
     * Converts the settings to command line arguments for `swift build`.
     * Each `define` becomes `-Xswiftc -D -Xswiftc FLAG_NAME`.
     */
    internal fun toCommandLineArgs(): List<String> =
        defines.flatMap { listOf("-Xswiftc", "-D", "-Xswiftc", it) }

    companion object {
        /**
         * Creates new [SwiftSettings] instance using the provided [configure] block.
         */
        operator fun invoke(configure: SwiftSettings.() -> Unit = {}): SwiftSettings {
            return SwiftSettings().apply(configure)
        }
    }
}
