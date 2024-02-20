@file:Suppress("UnstableApiUsage")

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
    alias(libs.plugins.adamko.dokkatoo.html).apply(false)
    alias(libs.plugins.arturbosch.detekt).apply(false)
    alias(libs.plugins.vanniktech.mavenPublish).apply(false)
}

allprojects {
    group = "io.shortway.kobankat"
    version = "0.1.0" +
            "-" +
            rootProject.libs.versions.revenuecat.android.get() +
            "-" +
            rootProject.libs.versions.revenuecat.ios.get()

    plugins.withType<MavenPublishPlugin> {
        configure<MavenPublishBaseExtension> {
            publishToMavenCentral(SonatypeHost.DEFAULT, automaticRelease = true)
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
    }
}