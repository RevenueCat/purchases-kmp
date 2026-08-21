package com.revenuecat.purchases.kmp.mappings.ktx

import kotlinx.cinterop.convert
import platform.darwin.NSInteger
import platform.darwin.NSIntegerMax
import platform.darwin.NSIntegerMin

/**
 * Converts to the platform's `NSInteger`, which is 32 bits on watchosArm64 (arm64_32). Values
 * outside that range are clamped, as `convert()` alone would silently wrap them.
 */
internal fun Long.toNSInteger(): NSInteger =
    coerceIn(NSIntegerMin.toLong(), NSIntegerMax.toLong()).convert()
