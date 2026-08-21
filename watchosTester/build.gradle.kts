import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.internal.utils.getLocalProperty

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.codingfeline.buildkonfig)
}

kotlin {
    listOf(
        watchosArm64(),
        watchosDeviceArm64(),
        watchosSimulatorArm64(),
    ).forEach { watchosTarget ->
        watchosTarget.binaries.framework {
            baseName = "WatchosTester"
            isStatic = true
            export(projects.core)
            export(projects.models)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            api(projects.models)
        }
    }
}

buildkonfig {
    packageName = "com.revenuecat.purchases.kmp.watchostester"

    defaultConfigs {
        buildConfigField(
            type = STRING,
            name = "apiKey",
            value = project.rootProject.getLocalProperty("revenuecat.apiKey.apple").orEmpty()
        )
    }
}
