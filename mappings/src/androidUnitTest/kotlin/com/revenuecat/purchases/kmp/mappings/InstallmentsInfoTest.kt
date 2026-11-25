package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.models.GoogleInstallmentsInfo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class InstallmentsInfoTest {

    @Test
    fun `maps Google installments info`() {
        val installmentsInfo = GoogleInstallmentsInfo(
            commitmentPaymentsCount = 12,
            renewalCommitmentPaymentsCount = 1,
        ).toInstallmentsInfo()

        assertEquals(12, installmentsInfo.commitmentPaymentsCount)
        assertEquals(1, installmentsInfo.renewalCommitmentPaymentsCount)
    }

    @Test
    fun `App Store only fields are null`() {
        val installmentsInfo = GoogleInstallmentsInfo(
            commitmentPaymentsCount = 12,
            renewalCommitmentPaymentsCount = 1,
        ).toInstallmentsInfo()

        assertNull(installmentsInfo.commitmentInstallmentPeriod)
        assertNull(installmentsInfo.installmentBillingPrice)
        assertNull(installmentsInfo.commitmentTotalPeriod)
        assertNull(installmentsInfo.commitmentTotalPrice)
        assertNull(installmentsInfo.billingPlanType)
    }
}
