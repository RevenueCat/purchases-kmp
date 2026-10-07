package com.revenuecat.purchases.kmp

import platform.Foundation.NSData
import platform.Foundation.NSJSONSerialization
import platform.Foundation.NSUserDefaults

/**
 * purchases-kmp 3.0.0 through 3.9.0 handed purchases-ios `NSUserDefaults(suiteName = null)`, so
 * installs upgrading from 2.x got a fresh app user ID in standard defaults while their 2.x ID
 * stayed in purchases-ios' own `com.revenuecat.user_defaults` suite. purchases-ios keeps using
 * standard as long as it holds an app user ID, so when the standard one is an anonymous ID with
 * no purchases this clears it before purchases-ios reads it, and the 2.x ID takes over again.
 */
internal object LegacyUserDefaultsMigration {
    private const val REVENUECAT_SUITE_NAME = "com.revenuecat.user_defaults"
    const val APP_USER_ID_KEY = "com.revenuecat.userdefaults.appUserID.new"
    const val CUSTOMER_INFO_KEY_PREFIX = "com.revenuecat.userdefaults.purchaserInfo."
    private const val ANONYMOUS_ID_PREFIX = "\$RCAnonymousID:"

    fun runIfNeeded(
        userDefaultsSuiteName: String?,
        standard: NSUserDefaults = NSUserDefaults.standardUserDefaults,
        revenueCatSuite: () -> NSUserDefaults = { NSUserDefaults(suiteName = REVENUECAT_SUITE_NAME) },
    ) {
        if (userDefaultsSuiteName != null) return
        val standardId = standard.stringForKey(APP_USER_ID_KEY) ?: return
        if (!standardId.startsWith(ANONYMOUS_ID_PREFIX)) return
        if (standard.dataForKey(CUSTOMER_INFO_KEY_PREFIX + standardId).hasPurchases()) return
        val suiteId = revenueCatSuite().stringForKey(APP_USER_ID_KEY) ?: return
        if (suiteId == standardId) return
        standard.removeObjectForKey(APP_USER_ID_KEY)
    }

    private fun NSData?.hasPurchases(): Boolean {
        val json = this?.let { NSJSONSerialization.JSONObjectWithData(it, 0u, null) } as? Map<*, *>
        val subscriber = json?.get("subscriber") as? Map<*, *> ?: return false
        return listOf("subscriptions", "non_subscriptions")
            .any { (subscriber[it] as? Map<*, *>)?.isNotEmpty() == true }
    }
}
