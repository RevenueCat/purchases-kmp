import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.configurationcache.extensions.capitalized

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.adamko.dokkatoo.html)
    alias(libs.plugins.arturbosch.detekt)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.gradleup.nmcp)
}

kotlin {
    explicitApi()
    targets.all {
        compilations.all {
            compilerOptions.configure {
                freeCompilerArgs.apply {
                    add("-Xexpect-actual-classes")
                }
            }
        }
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.java.get()
            }
        }

        publishLibraryVariants("release")
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.apply {
                if (name.lowercase().startsWith("ios")) {
                    optIn("kotlinx.cinterop.ExperimentalForeignApi")
                }
            }
        }
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            api(libs.revenuecat.android)
        }
    }

    cocoapods {
        version = "1.0"
        ios.deploymentTarget = "11.0"

        framework {
            baseName = "KobanKat"
            isStatic = true
        }

        pod("RevenueCat") {
            version = libs.versions.revenuecat.ios.get()
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
    }
}

android {
    namespace = "io.shortway.kobankat"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility(libs.versions.java.get())
        targetCompatibility(libs.versions.java.get())
    }
}

file("src")
    .listFiles { child -> child.isDirectory }
    .orEmpty()
    .also { sourceDirectories ->
        registerDetektTask(
            taskName = "detektAll",
            taskDescription = "Runs Detekt on all source sets.",
            reportName = "all",
            sourceDirs = files(sourceDirectories)
        )

        sourceDirectories.forEach { sourceDir -> registerDetektTask(sourceDir) }
    }

private fun registerDetektTask(sourceDir: File) =
    registerDetektTask(sourceDir.name)

private fun registerDetektTask(sourceDir: String) =
    registerDetektTask(
        taskName = "detekt${sourceDir.capitalized()}",
        taskDescription = "Runs Detekt on the $sourceDir source set.",
        reportName = sourceDir,
        sourceDirs = files("src/$sourceDir/kotlin")
    )

private fun registerDetektTask(
    taskName: String,
    taskDescription: String,
    reportName: String,
    sourceDirs: ConfigurableFileCollection,
) =
    tasks.register<Detekt>(taskName) {
        description = taskDescription
        setSource(sourceDirs)
        config = files("$rootDir/config/detekt/detekt.yml")
        reports {
            html.outputLocation = file("build/reports/detekt/$reportName.html")
            xml.outputLocation = file("build/reports/detekt/$reportName.xml")
        }
    }
