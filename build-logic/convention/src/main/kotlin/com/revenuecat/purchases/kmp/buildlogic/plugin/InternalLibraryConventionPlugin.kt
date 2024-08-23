package com.revenuecat.purchases.kmp.buildlogic.plugin

import com.revenuecat.purchases.kmp.buildlogic.convention.configureAndroid
import com.revenuecat.purchases.kmp.buildlogic.convention.configureKotlin
import com.revenuecat.purchases.kmp.buildlogic.convention.configurePlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A build convention plugin to be applied to all internal library modules, to reduce duplication
 * in build scripts.
 */
class InternalLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        configurePlugins(dokka = false)
        configureKotlin()
        configureAndroid()
    }

}
