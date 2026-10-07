package com.revenuecat.purchases.kmp

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserDefaultsSuiteNameTest {

    @Test
    fun `null suite name yields no user defaults so purchases-ios picks its own suite`() {
        assertNull((null as String?).toUserDefaults())
    }

    @Test
    fun `non-null suite name yields user defaults`() {
        assertNotNull("com.revenuecat.test".toUserDefaults())
    }
}
