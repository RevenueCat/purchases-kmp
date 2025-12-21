package com.revenuecat.purchases.kmp.buildlogic.plugin

import com.revenuecat.purchases.kmp.buildlogic.convention.configureAndroid
import com.revenuecat.purchases.kmp.buildlogic.convention.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A build convention plugin to be applied to all library modules, to reduce duplication in build
 * scripts.
 */
class RevenueCatLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val extension = extensions.create("revenueCat", RevenueCatExtension::class.java)

        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.library")
            apply("dev.adamko.dokkatoo-html")
            apply("io.gitlab.arturbosch.detekt")
            apply("com.vanniktech.maven.publish")
        }

        configureKotlin()
        configureAndroid()

        // Disable dokka if needed.
        afterEvaluate {
            if (!extension.dokka) {
                tasks.matching { it.name.contains("dokka", ignoreCase = true) }.configureEach {
                    enabled = false
                }
            }
        }
    }
}
