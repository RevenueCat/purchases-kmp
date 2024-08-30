package com.revenuecat.purchases.kmp.buildlogic.convention

import com.android.build.gradle.LibraryExtension
import com.revenuecat.purchases.kmp.buildlogic.ktx.getVersion
import com.revenuecat.purchases.kmp.buildlogic.ktx.versionCatalog
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureAndroid() {
    val libs = versionCatalog
    extensions.configure<LibraryExtension> {
        compileSdk = libs.getVersion("android-compileSdk").toInt()
        defaultConfig {
            minSdk = libs.getVersion("android-minSdk").toInt()
        }
        compileOptions {
            val javaVersion = libs.getVersion("java")
            sourceCompatibility(javaVersion)
            targetCompatibility(javaVersion)
        }
    }
}
