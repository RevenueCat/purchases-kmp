package com.revenuecat.purchases.kmp.buildlogic.convention

import org.gradle.api.plugins.PluginAware

internal fun PluginAware.configurePlugins(dokka: Boolean) {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("com.android.library")
        if (dokka) apply("dev.adamko.dokkatoo-html")
        apply("io.gitlab.arturbosch.detekt")
        apply("com.vanniktech.maven.publish")
    }
}
