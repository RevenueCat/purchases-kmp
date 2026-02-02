import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.revenuecat.purchases.kmp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.dokkatoo.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.mavenPublish.gradlePlugin)
    
    testImplementation(gradleTestKit())
    testImplementation(libs.junit.api)
    testImplementation(libs.junit.core)
    testImplementation(libs.kotlin.test.junit)

    testRuntimeOnly(libs.junit.engine)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
    // Provide the main project root directory to tests (build-logic is a composite build,
    // so rootProject.rootDir refers to build-logic, not the main project)
    systemProperty("mainProjectDir", rootProject.rootDir.parentFile.absolutePath)
}

gradlePlugin {
    plugins {
        register("Library") {
            id = "revenuecat-library"
            implementationClass =
                "com.revenuecat.purchases.kmp.buildlogic.plugin.RevenueCatLibraryConventionPlugin"
        }
    }
}
