package com.revenuecat.purchases.kmp.apitester

/**
 * Usage:
 * ```kotlin
 * when (enum) {
 *  A,
 *  B -> {}
 * }.exhaustive
 * ```
 *
 * Inspired by [this](https://proandroiddev.com/til-when-is-when-exhaustive-31d69f630a8b) blog.
 *
 */
internal val <T> T.exhaustive: T
    get() = this
