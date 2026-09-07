import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.internal.utils.getLocalProperty

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.codingfeline.buildkonfig)
}

kotlin {
    macosArm64().binaries.framework {
        baseName = "MacosTester"
        // A static framework makes Xcode link Kotlin/Native's prebuilt platform caches, which
        // under Kotlin 2.3.20 reference symbols absent from the macOS 15.5 SDK in Xcode 16.4
        // (_NSCalendarIdentifierBangla, _xpc_connection_set_peer_requirement, from
        // liborg.jetbrains.kotlin.native.platform.Foundation-cache.a). Going dynamic moves that
        // link into Kotlin/Native, which resolves them. Revert to static once CI's Xcode ships an
        // SDK at least as new as the one those caches were built against. Note that no CI job
        // then covers Xcode linking a static Kotlin framework, which is what consumers do.
        isStatic = false
        export(projects.core)
        export(projects.models)
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            api(projects.models)
        }
    }
}

buildkonfig {
    packageName = "com.revenuecat.purchases.kmp.macostester"

    defaultConfigs {
        buildConfigField(
            type = STRING,
            name = "apiKey",
            value = project.rootProject.getLocalProperty("revenuecat.apiKey.apple").orEmpty()
        )
    }
}
