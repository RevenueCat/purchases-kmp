import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    id("revenuecat-library")
    alias(libs.plugins.codingfeline.buildkonfig)
}

revenueCat {
    dokka = true
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.models)
        }
        androidMain.dependencies {
            api(libs.androidx.startup)
            implementation(libs.revenuecat.android)
            implementation(projects.mappings)
        }
        iosMain.dependencies {
            implementation(projects.knCore)
            implementation(projects.mappings)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test.annotations)
            implementation(libs.kotlin.test.assertions)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }
    }
}

android {
    namespace = "com.revenuecat.purchases.kmp"
}

buildkonfig {
    packageName = "com.revenuecat.purchases.kmp"

    defaultConfigs {
        buildConfigField(STRING, "platformFlavor", "kmp")
        buildConfigField(STRING, "revenuecatKmpVersion", libs.versions.revenuecat.kmp.get())
    }
}
