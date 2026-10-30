package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kn.core.additional.InstallmentsInfoPrices
import platform.darwin.NSObject
import kotlin.test.Test
import kotlin.test.assertNull

class InstallmentsInfoTest {

    @Test
    fun `price helpers return null for objects that are not installments info`() {
        assertNull(InstallmentsInfoPrices.installmentBillingPrice(NSObject()))
        assertNull(InstallmentsInfoPrices.commitmentTotalPrice(NSObject()))
    }
}
