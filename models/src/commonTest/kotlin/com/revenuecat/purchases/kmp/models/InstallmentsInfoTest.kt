package com.revenuecat.purchases.kmp.models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class InstallmentsInfoTest {

    @Test
    fun `two-argument constructor leaves iOS only fields null`() {
        val installmentsInfo = InstallmentsInfo(
            commitmentPaymentsCount = 6,
            renewalCommitmentPaymentsCount = 2,
        )

        assertEquals(6, installmentsInfo.commitmentPaymentsCount)
        assertEquals(2, installmentsInfo.renewalCommitmentPaymentsCount)
        assertNull(installmentsInfo.commitmentInstallmentPeriod)
        assertNull(installmentsInfo.installmentBillingPrice)
        assertNull(installmentsInfo.commitmentTotalPeriod)
        assertNull(installmentsInfo.commitmentTotalPrice)
        assertNull(installmentsInfo.billingPlanType)
    }

    @Test
    fun `full constructor sets every field`() {
        val installmentPeriod = Period(value = 1, unit = PeriodUnit.MONTH)
        val installmentPrice = Price(formatted = "$9.99", amountMicros = 9_990_000, currencyCode = "USD")
        val totalPeriod = Period(value = 1, unit = PeriodUnit.YEAR)
        val totalPrice = Price(formatted = "$119.88", amountMicros = 119_880_000, currencyCode = "USD")

        val installmentsInfo = InstallmentsInfo(
            commitmentPaymentsCount = 12,
            renewalCommitmentPaymentsCount = 12,
            commitmentInstallmentPeriod = installmentPeriod,
            installmentBillingPrice = installmentPrice,
            commitmentTotalPeriod = totalPeriod,
            commitmentTotalPrice = totalPrice,
            billingPlanType = BillingPlanType.MONTHLY,
        )

        assertEquals(12, installmentsInfo.commitmentPaymentsCount)
        assertEquals(12, installmentsInfo.renewalCommitmentPaymentsCount)
        assertEquals(installmentPeriod, installmentsInfo.commitmentInstallmentPeriod)
        assertEquals(installmentPrice, installmentsInfo.installmentBillingPrice)
        assertEquals(totalPeriod, installmentsInfo.commitmentTotalPeriod)
        assertEquals(totalPrice, installmentsInfo.commitmentTotalPrice)
        assertEquals(BillingPlanType.MONTHLY, installmentsInfo.billingPlanType)
    }
}
