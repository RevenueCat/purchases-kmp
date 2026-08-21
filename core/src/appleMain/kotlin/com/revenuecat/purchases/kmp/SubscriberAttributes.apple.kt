package com.revenuecat.purchases.kmp

/**
 * Maps KMP subscriber attributes onto the iOS SDK's NSDictionary-backed parameter type.
 *
 * Kotlin `null` values would otherwise become `NSNull` and crash when the native API tries to
 * cast them to `NSString`. Empty string is the iOS SDK's documented representation for deleting
 * an attribute.
 */
internal fun Map<String, String?>.toIosSubscriberAttributes(): Map<Any?, *> =
    mapValues { (_, value) -> value ?: "" }
