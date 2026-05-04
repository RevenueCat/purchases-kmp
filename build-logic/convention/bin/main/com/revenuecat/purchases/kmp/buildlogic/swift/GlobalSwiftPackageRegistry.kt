package com.revenuecat.purchases.kmp.buildlogic.swift

import com.revenuecat.purchases.kmp.buildlogic.swift.model.SwiftDependency
import org.gradle.api.Project

/**
 * A global registry that tracks all Swift targets declared across all Gradle projects.
 * This is mainly used to auto-detect module dependencies between Swift targets.
 *
 * This registry is created on the root project and accessed by all subprojects.
 */
open class GlobalSwiftPackageRegistry {
    /**
     * All registered Swift targets, keyed by target name.
     * Each entry contains the owning project and the dependency configuration.
     */
    internal val targets = mutableMapOf<String, RegisteredSwiftTarget>()

    /**
     * A registered Swift target and its owning project.
     */
    internal data class RegisteredSwiftTarget(
        val project: Project,
        val dependency: SwiftDependency
    )

    /**
     * Register a Swift target.
     * @param target The Swift target name
     * @param project The Gradle project that owns this target
     * @param dependency The dependency configuration
     */
    internal fun register(target: String, project: Project, dependency: SwiftDependency) {
        val existing = targets[target]
        if (existing != null && existing.project != project) {
            error(
                "Swift target '$target' is declared in multiple projects: " +
                        "'${existing.project.path}' and '${project.path}'. " +
                        "Each Swift target should be owned by exactly one Gradle project."
            )
        }
        targets[target] = RegisteredSwiftTarget(project, dependency)
    }

    /**
     * Get all registered targets.
     */
    internal fun getAllTargets(): Map<String, RegisteredSwiftTarget> = targets.toMap()

    /**
     * Find which project owns a given target, if any.
     */
    internal fun findOwner(targetName: String): RegisteredSwiftTarget? = targets[targetName]
}

/**
 * Get or create the global Swift package registry on the root project.
 */
internal fun Project.getOrCreateGlobalSwiftRegistry(): GlobalSwiftPackageRegistry {
    return rootProject.extensions.findByType(GlobalSwiftPackageRegistry::class.java)
        ?: rootProject.extensions.create(
            "globalSwiftPackageRegistry",
            GlobalSwiftPackageRegistry::class.java
        )
}
