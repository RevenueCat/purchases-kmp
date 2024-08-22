package com.revenuecat.purchases.kmp.buildlogic.plugin

import com.revenuecat.purchases.kmp.buildlogic.convention.configureAndroid
import com.revenuecat.purchases.kmp.buildlogic.convention.configureKotlin
import com.revenuecat.purchases.kmp.buildlogic.convention.configurePlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A build convention plugin to be applied to all public library modules, to reduce duplication
 * in build scripts.
 */
class PublicLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        configurePlugins(dokka = true)
        configureKotlin()
        configureAndroid()
    }

}
