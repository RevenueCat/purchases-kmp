package com.revenuecat.purchases.kmp

/**
 * This annotation marks RevenueCat APIs that are internal and not meant for public consumption.
 *
 * APIs marked with this annotation may change frequently and without warning, and are not
 * covered by semantic versioning guarantees. Do not use in your own code.
 */
@Retention(value = AnnotationRetention.BINARY)
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an internal RevenueCat API that may change frequently and without warning. " +
        "No compatibility guarantees are provided. It is strongly discouraged to use this API.",
)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS,
    AnnotationTarget.CONSTRUCTOR,
)
public annotation class InternalRevenueCatApi
