package com.revenuecat.purchases.kmp.buildlogic.swift

import org.gradle.api.Project

/**
 * A global registry that tracks all Swift targets declared across all Gradle projects.
 * This is used to:
 * 1. Auto-detect module dependencies between Swift targets
 * 2. Avoid duplicate compilations by sharing the SPM build cache
 * 3. Wire up task dependencies between projects
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
    data class RegisteredSwiftTarget(
        val project: Project,
        val dependency: SwiftDependency
    )

    /**
     * Register a Swift target.
     * @param target The Swift target name
     * @param project The Gradle project that owns this target
     * @param dependency The dependency configuration
     */
    fun register(target: String, project: Project, dependency: SwiftDependency) {
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
    fun getAllTargets(): Map<String, RegisteredSwiftTarget> = targets.toMap()

    /**
     * Find which project owns a given target, if any.
     */
    fun findOwner(targetName: String): RegisteredSwiftTarget? = targets[targetName]
}

/**
 * Get or create the global Swift package registry on the root project.
 */
internal fun Project.getOrCreateGlobalSwiftRegistry(): GlobalSwiftPackageRegistry {
    return rootProject.extensions.findByType(GlobalSwiftPackageRegistry::class.java)
        ?: rootProject.extensions.create("globalSwiftPackageRegistry", GlobalSwiftPackageRegistry::class.java)
}

