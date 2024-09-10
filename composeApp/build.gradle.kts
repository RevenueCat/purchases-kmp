import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.internal.utils.getLocalProperty

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.codingfeline.buildkonfig)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.java.get()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                if (name.lowercase().startsWith("ios")) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                }
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(projects.core)
            implementation(projects.result)
            implementation(projects.either)
            implementation(projects.datetime)
            implementation(projects.revenuecatui)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp.sample"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.revenuecat.purchases_sample"
        minSdk = 24
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
        sourceCompatibility(libs.versions.java.get())
        targetCompatibility(libs.versions.java.get())
    }
}

buildkonfig {
    packageName = "com.revenuecat.purchases.kmp.sample"

    defaultConfigs {
        // apiKey is overridden in targetConfigs.
        buildConfigField(type = STRING, name = "apiKey", value = "")
        buildConfigField(
            type = STRING,
            name = "appUserId",
            value = project.rootProject.getLocalProperty("revenuecat.appUserId").orEmpty()
        )
    }
    targetConfigs {
        create("android") {
            buildConfigField(
                type = STRING,
                name = "apiKey",
                value = project.rootProject
                    .getLocalProperty("revenuecat.apiKey.google")
                    .orEmpty()
            )
        }
        listOf(
            "iosX64",
            "iosArm64",
            "iosSimulatorArm64",
        ).forEach { iosTarget ->
            create(iosTarget) {
                buildConfigField(
                    type = STRING,
                    name = "apiKey",
                    value = project.rootProject
                        .getLocalProperty("revenuecat.apiKey.apple")
                        .orEmpty()
                )
            }
        }
    }
}
