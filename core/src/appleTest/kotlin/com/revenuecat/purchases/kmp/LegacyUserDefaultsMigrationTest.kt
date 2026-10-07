package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.LegacyUserDefaultsMigration.APP_USER_ID_KEY
import com.revenuecat.purchases.kmp.LegacyUserDefaultsMigration.CUSTOMER_INFO_KEY_PREFIX
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSUserDefaults
import platform.Foundation.dataUsingEncoding
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LegacyUserDefaultsMigrationTest {

    private val anonymousInStandard = "\$RCAnonymousID:aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    private val anonymousInSuite = "\$RCAnonymousID:bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    private val standard = NSUserDefaults(suiteName = "com.revenuecat.kmp.test.standard")
    private val revenueCatSuite = NSUserDefaults(suiteName = "com.revenuecat.kmp.test.suite")

    @BeforeTest
    @AfterTest
    fun clearStores() {
        listOf(standard, revenueCatSuite).forEach { store ->
            store.dictionaryRepresentation().keys.forEach { store.removeObjectForKey(it as String) }
        }
    }

    private fun seed(standardId: String? = anonymousInStandard, suiteId: String? = anonymousInSuite) {
        standardId?.let { standard.setObject(it, APP_USER_ID_KEY) }
        suiteId?.let { revenueCatSuite.setObject(it, APP_USER_ID_KEY) }
    }

    private fun cacheCustomerInfo(json: String) {
        @Suppress("CAST_NEVER_SUCCEEDS")
        val data = (json as NSString).dataUsingEncoding(NSUTF8StringEncoding)
        standard.setObject(data, CUSTOMER_INFO_KEY_PREFIX + anonymousInStandard)
    }

    private fun run(suiteName: String? = null) =
        LegacyUserDefaultsMigration.runIfNeeded(suiteName, standard) { revenueCatSuite }

    private fun assertStores(standardId: String?, suiteId: String?) {
        assertEquals(standardId, standard.stringForKey(APP_USER_ID_KEY))
        assertEquals(suiteId, revenueCatSuite.stringForKey(APP_USER_ID_KEY))
    }

    @Test
    fun `does nothing when a suite name is configured`() {
        seed()
        run(suiteName = "com.example.app")
        assertStores(standardId = anonymousInStandard, suiteId = anonymousInSuite)
    }

    @Test
    fun `does nothing when only standard holds an app user ID`() {
        seed(suiteId = null)
        run()
        assertStores(standardId = anonymousInStandard, suiteId = null)
    }

    @Test
    fun `does nothing when only the RevenueCat suite holds an app user ID`() {
        seed(standardId = null)
        run()
        assertStores(standardId = null, suiteId = anonymousInSuite)
    }

    @Test
    fun `does nothing when both stores hold the same app user ID`() {
        seed(suiteId = anonymousInStandard)
        run()
        assertStores(standardId = anonymousInStandard, suiteId = anonymousInStandard)
    }

    @Test
    fun `anonymous ID in standard without cached customer info loses to the ID in the suite`() {
        seed()
        run()
        assertStores(standardId = null, suiteId = anonymousInSuite)
    }

    @Test
    fun `anonymous ID in standard without purchases loses to the ID in the suite`() {
        seed()
        cacheCustomerInfo("""{"subscriber":{"subscriptions":{},"non_subscriptions":{}}}""")
        run()
        assertStores(standardId = null, suiteId = anonymousInSuite)
    }

    @Test
    fun `anonymous ID in standard with a subscription stays in standard`() {
        seed()
        cacheCustomerInfo("""{"subscriber":{"subscriptions":{"monthly":{}},"non_subscriptions":{}}}""")
        run()
        assertStores(standardId = anonymousInStandard, suiteId = anonymousInSuite)
    }

    @Test
    fun `anonymous ID in standard with a non-subscription purchase stays in standard`() {
        seed()
        cacheCustomerInfo("""{"subscriber":{"subscriptions":{},"non_subscriptions":{"coins":[{}]}}}""")
        run()
        assertStores(standardId = anonymousInStandard, suiteId = anonymousInSuite)
    }

    @Test
    fun `cached customer info that is not valid JSON counts as no purchases`() {
        seed()
        cacheCustomerInfo("not json")
        run()
        assertStores(standardId = null, suiteId = anonymousInSuite)
    }

    @Test
    fun `identified ID in standard stays in standard`() {
        seed(standardId = "user-42")
        run()
        assertStores(standardId = "user-42", suiteId = anonymousInSuite)
    }
}
