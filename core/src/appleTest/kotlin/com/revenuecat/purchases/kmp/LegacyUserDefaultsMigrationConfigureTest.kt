package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.LegacyUserDefaultsMigration.APP_USER_ID_KEY
import platform.Foundation.NSUserDefaults
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Runs the migration through the real `configure`, against the real standard defaults and the
 * real `com.revenuecat.user_defaults` suite, so it covers purchases-ios reading the suite through
 * its own `UserDefaults` instance on the same launch.
 */
class LegacyUserDefaultsMigrationConfigureTest {

    private val anonymousInStandard = "\$RCAnonymousID:cccccccccccccccccccccccccccccccc"
    private val anonymousInSuite = "\$RCAnonymousID:dddddddddddddddddddddddddddddddd"
    private val standard = NSUserDefaults.standardUserDefaults
    private val revenueCatSuite = NSUserDefaults(suiteName = "com.revenuecat.user_defaults")

    @BeforeTest
    @AfterTest
    fun clearStores() {
        standard.removeObjectForKey(APP_USER_ID_KEY)
        revenueCatSuite.removeObjectForKey(APP_USER_ID_KEY)
    }

    @Test
    fun `configure runs as the suite's app user ID when standard holds an anonymous ID without purchases`() {
        standard.setObject(anonymousInStandard, APP_USER_ID_KEY)
        revenueCatSuite.setObject(anonymousInSuite, APP_USER_ID_KEY)

        // purchases-ios returns the existing instance when configure repeats an equal configuration,
        // so this key must differ from the one PurchasesConfigureUserDefaultsTest uses.
        val purchases = Purchases.configure(PurchasesConfiguration(apiKey = "test_api_key_migration"))

        assertEquals(anonymousInSuite, purchases.appUserID)
        assertNull(standard.stringForKey(APP_USER_ID_KEY))
    }
}
