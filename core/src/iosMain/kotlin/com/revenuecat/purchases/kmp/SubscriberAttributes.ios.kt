package com.revenuecat.purchases.kmp

/**
 * Maps KMP subscriber attributes onto the iOS SDK's NSDictionary-backed parameter type.
 *
 * Kotlin `null` values would otherwise become `NSNull` and crash when the native API tries to
 * cast them to `NSString`. Empty string is the iOS SDK's documented representation for deleting
 * an attribute.
 *
 * The return type is declared as `Map<Any?, *>` because that is the ObjC interop parameter type
 * for `RCPurchases.setAttributes`, and [Map] is invariant, so `Map<String, String>` is not a
 * subtype of it.
 */
internal fun Map<String, String?>.toIosSubscriberAttributes(): Map<Any?, *> =
    mapValues { (_, value) -> value ?: "" }
