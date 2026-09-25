package com.revenuecat.purchases.kmp

import platform.Foundation.NSUserDefaults
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Exercises purchases-ios' `UserDefaults.computeDefault()`: with no suite configured it must
 * cache the app user ID in its own `com.revenuecat.user_defaults` suite, where 2.x stored it,
 * not in standard defaults.
 */
class PurchasesConfigureUserDefaultsTest {

    private val appUserIdKey = "com.revenuecat.userdefaults.appUserID.new"
    private val standard = NSUserDefaults.standardUserDefaults
    private val revenueCatSuite = NSUserDefaults(suiteName = "com.revenuecat.user_defaults")

    @BeforeTest
    fun clearStores() {
        standard.removeObjectForKey(appUserIdKey)
        revenueCatSuite.removeObjectForKey(appUserIdKey)
    }

    @AfterTest
    fun cleanUp() = clearStores()

    @Test
    fun `configure without a suite name caches the app user ID in the RevenueCat suite`() {
        Purchases.configure(PurchasesConfiguration(apiKey = "test_api_key"))

        assertNull(standard.stringForKey(appUserIdKey))
        assertNotNull(revenueCatSuite.stringForKey(appUserIdKey))
    }
}
