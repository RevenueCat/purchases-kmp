import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.internal.utils.getLocalProperty

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("com.revenuecat.purchases:purchases-kmp-core:${rootLibs.versions.revenuecat.kmp.get()}")
            implementation("com.revenuecat.purchases:purchases-kmp-ui:${rootLibs.versions.revenuecat.kmp.get()}")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.revenuecat.automatedsdktests"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.revenuecat.automatedsdktests"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

buildkonfig {
    packageName = "com.revenuecat.automatedsdktests"

    defaultConfigs {
        buildConfigField(STRING, "apiKey", "")
        buildConfigField(STRING, "appUserId", project.rootProject.getLocalProperty("revenuecat.appUserId").orEmpty())
    }
    targetConfigs {
        create("android") {
            buildConfigField(STRING, "apiKey", project.rootProject.getLocalProperty("revenuecat.apiKey.google").orEmpty())
        }
        listOf("iosArm64", "iosSimulatorArm64").forEach { target ->
            create(target) {
                buildConfigField(STRING, "apiKey", project.rootProject.getLocalProperty("revenuecat.apiKey.apple").orEmpty())
            }
        }
    }
}

