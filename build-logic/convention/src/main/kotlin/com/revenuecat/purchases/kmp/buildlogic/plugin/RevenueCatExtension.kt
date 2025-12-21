package com.revenuecat.purchases.kmp.buildlogic.plugin

/**
 * Extension to configure the RevenueCat library plugin.
 *
 * Usage:
 * ```kotlin
 * revenueCat {
 *     dokka = true
 * }
 * ```
 */
open class RevenueCatExtension {
    /**
     * Whether to enable Dokka documentation generation. Defaults to false. Set to true for public
     * libraries.
     */
    var dokka: Boolean = false
}
