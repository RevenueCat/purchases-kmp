/*
 * Created by Antonio Pallares on 3/10/26.
 * Copyright (c) 2026 RevenueCat, Inc. All rights reserved.
 */

import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.artifacts.component.ProjectComponentIdentifier

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.codingfeline.buildkonfig)
}

val releasedSdkVersion = providers.gradleProperty("SDK_UPDATE_TESTER_RELEASE_VERSION").orNull
require(releasedSdkVersion == null || releasedSdkVersion.matches(Regex("[0-9]+\\.[0-9]+\\.[0-9]+"))) {
    "SDK_UPDATE_TESTER_RELEASE_VERSION must be a stable release version"
}
val variant = if (releasedSdkVersion == null) "local" else "release"
layout.buildDirectory.set(layout.projectDirectory.dir("build/$variant"))

kotlin {
    androidTarget()
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "SDKUpdateTester"
            isStatic = true
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            if (releasedSdkVersion != null) {
                implementation("com.revenuecat.purchases:purchases-kmp-core:$releasedSdkVersion")
            } else {
                implementation(projects.core)
            }
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "com.revenuecat.sdkupdatetester"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    defaultConfig {
        applicationId = "com.revenuecat.SDKUpdateTester"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = if (releasedSdkVersion != null) 1 else 2
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility(libs.versions.java.get())
        targetCompatibility(libs.versions.java.get())
    }
}

buildkonfig {
    packageName = "com.revenuecat.sdkupdatetester"
    defaultConfigs {
        buildConfigField(STRING, "apiKey", providers.environmentVariable("MAESTRO_TEST_STORE_API_KEY").getOrElse(""))
    }
}

mapOf(
    "Android" to "debugRuntimeClasspath",
    "Ios" to "iosSimulatorArm64CompileKlibraries",
).forEach { (platform, configurationName) ->
    tasks.register("verifySdkUpdate${platform}Dependency") {
        val configuration = configurations.named(configurationName)
        val report = layout.buildDirectory.file("reports/resolved-dependencies/$platform.txt")
        doLast {
            val components = configuration.get().incoming.resolutionResult.allComponents.map { it.id }
            val modules = components.filterIsInstance<ModuleComponentIdentifier>().filter {
                it.group == "com.revenuecat.purchases" && it.module.startsWith("purchases-kmp-")
            }
            val projects = components.filterIsInstance<ProjectComponentIdentifier>().filter {
                it.projectPath in setOf(":core", ":models", ":mappings", ":kn-core")
            }
            if (releasedSdkVersion != null) {
                check(projects.isEmpty()) { "Released build resolved local KMP projects: $projects" }
                check(modules.any { it.module.startsWith("purchases-kmp-core") }) {
                    "Released KMP core artifact was not resolved"
                }
                check(modules.all { it.version == releasedSdkVersion }) {
                    "Released build resolved unexpected KMP versions: $modules"
                }
            } else {
                check(projects.any { it.projectPath == ":core" }) { "Local KMP core project was not resolved" }
                check(modules.isEmpty()) { "Local build resolved published KMP artifacts: $modules" }
            }
            report.get().asFile.apply {
                parentFile.mkdirs()
                writeText(components.filter { it.displayName.contains("revenuecat") || it in projects }
                    .joinToString("\n") { it.displayName } + "\n")
            }
        }
    }
}
