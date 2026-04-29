@file:Suppress("UnstableApiUsage")

import org.gradle.api.artifacts.repositories.InclusiveRepositoryContentDescriptor

fun InclusiveRepositoryContentDescriptor.googleHostedGroups() {
    includeGroupByRegex("androidx\\..*")
    includeGroupByRegex("com\\.android($|\\..*)")
    includeGroupByRegex("com\\.google\\.android\\.datatransport($|\\..*)")
    includeGroupByRegex("com\\.google\\.android\\.gms($|\\..*)")
    includeGroup("com.google.firebase")
    includeGroupByRegex("com\\.google\\.firebase\\..*")
    includeGroup("com.google.testing.platform")
}

fun InclusiveRepositoryContentDescriptor.mavenCentralHostedGroups() {
    includeGroup("com.beust")
    includeGroup("com.caverock")
    includeGroupByRegex("com\\.fasterxml($|\\..*)")
    includeGroup("com.github.ben-manes.caffeine")
    includeGroup("com.google.accompanist")
    includeGroup("com.google.android")
    includeGroup("com.google.api.grpc")
    includeGroupByRegex("com\\.google\\.auto($|\\..*)")
    includeGroupByRegex("com\\.google\\.code\\..*")
    includeGroup("com.google.crypto.tink")
    includeGroup("com.google.dagger")
    includeGroup("com.google.errorprone")
    includeGroup("com.google.flatbuffers")
    includeGroup("com.google.guava")
    includeGroup("com.google.j2objc")
    includeGroup("com.google.jimfs")
    includeGroup("com.google.protobuf")
    includeGroup("com.googlecode.juniversalchardet")
    includeGroup("com.revenuecat.purchases")
    includeGroupByRegex("com\\.squareup($|\\..*)")
    includeGroupByRegex("com\\.sun($|\\..*)")
    includeGroup("com.vanniktech")
    includeGroup("commons-codec")
    includeGroup("commons-io")
    includeGroup("commons-logging")
    includeGroup("dev.adamko.dokkatoo")
    includeGroup("dev.drewhamilton.poko")
    includeGroup("io.arrow-kt")
    includeGroup("io.coil-kt")
    includeGroupByRegex("io\\.github\\..*")
    includeGroup("io.gitlab.arturbosch.detekt")
    includeGroup("io.grpc")
    includeGroup("io.netty")
    includeGroup("io.opencensus")
    includeGroup("io.opentelemetry")
    includeGroup("io.perfmark")
    includeGroup("it.unimi.dsi")
    includeGroupByRegex("jakarta\\..*")
    includeGroupByRegex("javax\\..*")
    includeGroup("junit")
    includeGroupByRegex("net\\..*")
    includeGroupByRegex("org\\.apache($|\\..*)")
    includeGroup("org.apiguardian")
    includeGroup("org.bitbucket.b_c")
    includeGroup("org.bouncycastle")
    includeGroup("org.checkerframework")
    includeGroupByRegex("org\\.codehaus($|\\..*)")
    includeGroup("org.commonmark")
    includeGroup("org.eclipse.ee4j")
    includeGroup("org.freemarker")
    includeGroup("org.glassfish.jaxb")
    includeGroup("org.hamcrest")
    includeGroup("org.jdom")
    includeGroup("org.jetbrains")
    includeGroup("org.jetbrains.androidx.lifecycle")
    includeGroup("org.jetbrains.androidx.savedstate")
    includeGroupByRegex("org\\.jetbrains\\.compose($|\\..*)")
    includeGroup("org.jetbrains.dokka")
    includeGroup("org.jetbrains.intellij.deps")
    includeGroup("org.jetbrains.kotlin")
    includeGroup("org.jetbrains.kotlinx")
    includeGroup("org.jetbrains.skiko")
    includeGroup("org.jsoup")
    includeGroup("org.jspecify")
    includeGroupByRegex("org\\.junit($|\\..*)")
    includeGroup("org.jvnet.staxex")
    includeGroup("org.opentest4j")
    includeGroupByRegex("org\\.ow2($|\\..*)")
    includeGroup("org.slf4j")
    includeGroup("org.snakeyaml")
    includeGroup("org.sonatype.oss")
    includeGroup("org.tensorflow")
}

fun InclusiveRepositoryContentDescriptor.pluginPortalHostedGroups() {
    includeGroup("com.codingfeline.buildkonfig")
    includeGroup("com.vanniktech.maven.publish")
    includeGroup("dev.adamko.dokkatoo-html")
    includeGroup("dev.iurysouza")
    includeGroup("dev.iurysouza.modulegraph")
    includeGroup("org.gradle.kotlin")
    includeGroup("org.gradle.kotlin.kotlin-dsl")
    includeGroup("org.jetbrains.kotlin.multiplatform")
    includeGroup("org.jetbrains.kotlin.plugin.compose")
    includeGroup("org.jetbrains.kotlinx.binary-compatibility-validator")
}

pluginManagement {
    repositories {
        exclusiveContent {
            forRepository { google() }
            filter { googleHostedGroups() }
        }
        exclusiveContent {
            forRepository { mavenCentral() }
            filter { mavenCentralHostedGroups() }
        }
        exclusiveContent {
            forRepository { gradlePluginPortal() }
            filter { pluginPortalHostedGroups() }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS

    repositories {
        exclusiveContent {
            forRepository { google() }
            filter { googleHostedGroups() }
        }
        exclusiveContent {
            forRepository { mavenCentral() }
            filter { mavenCentralHostedGroups() }
        }
    }
}
