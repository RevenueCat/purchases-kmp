package io.shortway.kobankat.buildlogic.ktx

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionConstraint

internal fun VersionCatalog.getVersion(alias: String): String =
    findVersion(alias).get().version

/**
 * Uses the same logic as VersionFactory.doGetVersion().
 */
internal val VersionConstraint.version: String
    get() = requiredVersion.ifEmpty {
        strictVersion.ifEmpty {
            preferredVersion
        }
    }
