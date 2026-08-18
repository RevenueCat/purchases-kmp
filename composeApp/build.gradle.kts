import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.internal.utils.getLocalProperty
import org.jetbrains.kotlin.gradle.tasks.CInteropProcess
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.codingfeline.buildkonfig)
}

// Local-only manual test of a real rewarded-ad SSV flow (see RewardVerificationTestingScreen).
// GoogleMobileAds is vendored via Google's official SPM package
// (github.com/googleads/swift-package-manager-google-mobile-ads) rather than committed, since
// it's a ~37MB binary XCFramework. src/swift/GoogleMobileAdsVendor is a minimal wrapper package
// that depends on it purely so `swift build` resolves and checksum-verifies the binary; we still
// cinterop directly against the raw XCFramework below, since it's a prebuilt Objective-C binary
// with no Swift source of ours to run through the swiftPackage() cinterop pipeline.
val googleMobileAdsVendorDir = layout.projectDirectory.dir("src/swift/GoogleMobileAdsVendor").asFile
val googleMobileAdsScratchDir = layout.buildDirectory.dir("swift-vendor/GoogleMobileAdsVendor").get().asFile
val googleMobileAdsFrameworksDir = googleMobileAdsScratchDir.resolve(
    "artifacts/swift-package-manager-google-mobile-ads/GoogleMobileAds/GoogleMobileAds.xcframework"
)

val resolveGoogleMobileAds by tasks.registering {
    val vendorDir = googleMobileAdsVendorDir
    val scratchDir = googleMobileAdsScratchDir
    val markerFile = googleMobileAdsFrameworksDir.resolve("Info.plist")
    outputs.dir(googleMobileAdsFrameworksDir)
    doLast {
        if (markerFile.exists()) return@doLast

        // Plain ProcessBuilder (not providers.exec/project.exec) to avoid config-cache
        // "script object reference" serialization failures from this ad-hoc task.
        fun run(vararg command: String, workingDir: File? = null, extraEnv: Map<String, String> = emptyMap()): String {
            val process = ProcessBuilder(*command)
                .apply { workingDir?.let { directory(it) } }
                .apply { environment().putAll(extraEnv) }
                .start()
            val stdout = process.inputStream.bufferedReader().readText()
            val stderr = process.errorStream.bufferedReader().readText()
            check(process.waitFor() == 0) { "Command failed: ${command.joinToString(" ")}\n$stderr" }
            return stdout
        }

        val sdkPath = run("xcrun", "--sdk", "iphonesimulator", "--show-sdk-path").trim()
        run(
            "xcrun", "swift", "build",
            "--target", "GoogleMobileAdsVendor",
            "--configuration", "debug",
            "--triple", "arm64-apple-ios-simulator",
            "--scratch-path", scratchDir.absolutePath,
            "-Xswiftc", "-sdk", "-Xswiftc", sdkPath,
            "-Xcc", "-isysroot", "-Xcc", sdkPath,
            // Avoids trying to use the iOS SDK to parse Package.swift when building from Xcode.
            workingDir = vendorDir,
            extraEnv = mapOf("SDKROOT" to ""),
        )
    }
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
        val frameworkSlice = if (iosTarget.name == "iosArm64") "ios-arm64" else "ios-arm64_x86_64-simulator"
        val frameworkSearchDir = googleMobileAdsFrameworksDir.resolve(frameworkSlice)

        iosTarget.compilations.getByName("main") {
            cinterops {
                create("GoogleMobileAds") {
                    defFile(project.file("src/iosMain/cinterop/GoogleMobileAds.def"))
                    compilerOpts("-F${frameworkSearchDir.absolutePath}")
                }
            }
        }

        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            linkerOpts("-F${frameworkSearchDir.absolutePath}", "-framework", "GoogleMobileAds")
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
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(projects.core)
            implementation(projects.result)
            implementation(projects.either)
            implementation(projects.revenuecatui)
        }
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.google.mobileAds)
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

tasks.withType<CInteropProcess>().configureEach {
    if (name.contains("GoogleMobileAds")) {
        dependsOn(resolveGoogleMobileAds)
    }
}

tasks.withType<KotlinNativeLink>().configureEach {
    dependsOn(resolveGoogleMobileAds)
}
