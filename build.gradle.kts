@file:Suppress("UnstableApiUsage")

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import com.vanniktech.maven.publish.SonatypeHost
import dev.iurysouza.modulegraph.Orientation
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.configurationcache.extensions.capitalized

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
    alias(libs.plugins.kotlinx.binaryCompatibilityValidator)
    alias(libs.plugins.adamko.dokkatoo.html)
    alias(libs.plugins.arturbosch.detekt).apply(false)
    alias(libs.plugins.vanniktech.mavenPublish).apply(false)
    alias(libs.plugins.iurysouza.modulegraph)
}

allprojects {
    group = "com.revenuecat.purchases"
    version = rootProject.libs.versions.revenuecat.kmp.get()

    plugins.withType<MavenPublishPlugin> {
        configure<MavenPublishBaseExtension> {
            publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
            signAllPublications()

            // We override the artifact ID of :revenuecatui for consistency with the other SDKs. We
            // could not name our Gradle module :ui, because this somehow conflicts with compose.ui
            // in the iosMain source set. We can retry this at a later time.
            val artifactIdSuffix = when (project.name) {
                "revenuecatui" -> "ui"
                else -> project.name
            }

            coordinates(
                groupId = group.toString(),
                artifactId = "purchases-kmp-$artifactIdSuffix",
                version = version.toString()
            )
            pom {
                name.set("purchases-kmp-(${project.name})")
                description.set("Mobile subscriptions in hours, not months.")
                inceptionYear.set("2024")
                url.set("https://github.com/RevenueCat/purchases-kmp")
                licenses {
                    license {
                        name.set("The MIT License (MIT)")
                        url.set("http://opensource.org/licenses/MIT")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("revenuecat")
                        name.set("RevenueCat, Inc.")
                        url.set("https://www.revenuecat.com/")
                    }
                }
                scm {
                    url.set("https://github.com/RevenueCat/purchases-kmp")
                    connection.set("scm:git:git://github.com/RevenueCat/purchases-kmp.git")
                    developerConnection.set("scm:git:ssh://git@github.com/RevenueCat/purchases-kmp.git")
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

apiValidation {
    ignoredProjects.addAll(listOf("apiTester", "composeApp", "mappings"))

    @OptIn(kotlinx.validation.ExperimentalBCVApi::class)
    klib {
        enabled = true
    }
}

dependencies {
    dokkatoo(projects.core)
    dokkatoo(projects.datetime)
    dokkatoo(projects.either)
    dokkatoo(projects.models)
    dokkatoo(projects.result)
    dokkatoo(projects.revenuecatui)
}

moduleGraphConfig {
    fun Dependency.readmePath() = "./$name/README.md"
    fun Dependency.heading() = "## :${name} module dependency graph"
    val topToBottom = Orientation.TOP_TO_BOTTOM
    // Intentional root for all modules, so we always show the entire graph.
    val rootModuleRegex = ".*revenuecatui.*"

    listOf(
        projects.core,
        projects.mappings,
        projects.models,
        projects.revenuecatui,
    ).forEach { project ->
        // All graphs are equal, but we need to specify 1 main graph. So :core it is.
        if (project == projects.core) {
            readmePath.set(project.readmePath())
            heading = project.heading()
            orientation.set(topToBottom)
            rootModulesRegex.set(rootModuleRegex)
        } else graph(
            readmePath = project.readmePath(),
            heading = project.heading(),
        ) {
            orientation = topToBottom
            rootModulesRegex = rootModuleRegex
        }
    }
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
