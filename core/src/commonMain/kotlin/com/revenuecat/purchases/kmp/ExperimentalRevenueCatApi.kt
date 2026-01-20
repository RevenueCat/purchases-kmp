package com.revenuecat.purchases.kmp

/**
 * This annotation marks RevenueCat APIs that are experimental and may change in future versions.
 *
 * APIs marked with this annotation are subject to change without notice and may not follow
 * semantic versioning guarantees. Use with caution in production code.
 */
@RequiresOptIn(
    message = "This is an experimental RevenueCat API. It may be changed or removed in future releases.",
    level = RequiresOptIn.Level.WARNING
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
public annotation class ExperimentalRevenueCatApi
