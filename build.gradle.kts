@file:Suppress("UnstableApiUsage")

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import io.gitlab.arturbosch.detekt.Detekt
import nmcp.NmcpExtension
import nmcp.NmcpPlugin
import org.gradle.configurationcache.extensions.capitalized

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
    alias(libs.plugins.adamko.dokkatoo.html)
    alias(libs.plugins.arturbosch.detekt).apply(false)
    alias(libs.plugins.vanniktech.mavenPublish).apply(false)
    alias(libs.plugins.gradleup.nmcp).apply(false)
}

allprojects {
    group = "io.shortway.kobankat"
    version = "0.4.0" +
            "-" +
            rootProject.libs.versions.revenuecat.android.get() +
            "-" +
            rootProject.libs.versions.revenuecat.ios.get()

    // NmcpPlugin publishes to a local repo when running assemble, meaning we need signing
    // credentials for every assemble. This avoids that.
    if (gradle.startParameter.taskNames.contains("publishAllPublicationsToCentralPortal")) {
        // Remove when https://github.com/vanniktech/gradle-maven-publish-plugin/issues/722 is fixed.
        plugins.withType<NmcpPlugin> {
            configure<NmcpExtension>() {
                publishAllPublications {
                    username = System.getenv("ORG_GRADLE_PROJECT_mavenCentralUsername")
                    password = System.getenv("ORG_GRADLE_PROJECT_mavenCentralPassword")
                    publicationType = "AUTOMATIC"
                }
            }
        }
    }

    plugins.withType<MavenPublishPlugin> {
        configure<MavenPublishBaseExtension> {
            // Re-enable when https://github.com/vanniktech/gradle-maven-publish-plugin/issues/722
            // is fixed.
            // publishToMavenCentral(SonatypeHost.DEFAULT, automaticRelease = true)
            signAllPublications()

            coordinates(
                groupId = group.toString(),
                artifactId = "kobankat-${project.name}",
                version = version.toString()
            )
            pom {
                name.set("KobanKat (${project.name})")
                description.set("RevenueCat SDK for Kotlin Multiplatform")
                inceptionYear.set("2024")
                url.set("https://github.com/JayShortway/kobankat")
                licenses {
                    license {
                        name.set("The MIT License (MIT)")
                        url.set("http://opensource.org/licenses/MIT")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("JayShortway")
                        name.set("Jay Shortway")
                        url.set("https://github.com/JayShortway")
                    }
                }
                scm {
                    url.set("https://github.com/JayShortway/kobankat")
                    connection.set("scm:git:https://github.com/JayShortway/kobankat.git")
                    developerConnection.set("scm:git:ssh://git@github.com/JayShortway/kobankat.git")
                }
            }
        }

        // Register a Detekt task for all published modules.
        with(this@allprojects) {
            projectDir
                .resolve("src")
                .listFiles { child -> child.isDirectory }
                .orEmpty()
                .also { sourceDirectories ->
                    tasks.registerDetektTask(
                        taskName = "detektAll",
                        taskDescription = "Runs Detekt on all source sets.",
                        reportName = "all",
                        sourceDirs = files(sourceDirectories)
                    )

                    sourceDirectories.forEach { sourceDir ->
                        val sourceSet = sourceDir.name
                        tasks.registerDetektTask(
                            taskName = "detekt${sourceSet.capitalized()}",
                            taskDescription = "Runs Detekt on the $sourceSet source set.",
                            reportName = "$name${sourceSet.capitalized()}",
                            sourceDirs = files(sourceDir)
                        )
                    }
                }
        }
    }
}

dependencies {
    dokkatoo(projects.core)
    dokkatoo(projects.datetime)
    dokkatoo(projects.either)
    dokkatoo(projects.result)
}

private fun TaskContainer.registerDetektTask(
    taskName: String,
    taskDescription: String,
    reportName: String,
    sourceDirs: ConfigurableFileCollection,
) =
    register<Detekt>(taskName) {
        description = taskDescription
        setSource(sourceDirs.map { it.resolve("kotlin") })
        config = files("$rootDir/config/detekt/detekt.yml")
        reports {
            html.outputLocation = file("build/reports/detekt/$reportName.html")
            xml.outputLocation = file("build/reports/detekt/$reportName.xml")
        }
    }
