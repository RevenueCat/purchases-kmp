package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.testswift.InstallmentsInfoTestFactory
import com.revenuecat.purchases.kmp.models.BillingPlanType
import com.revenuecat.purchases.kmp.models.PeriodUnit
import com.revenuecat.purchases.kn.core.RCBillingPlanType
import com.revenuecat.purchases.kn.core.RCInstallmentsInfo
import com.revenuecat.purchases.kn.core.RCSubscriptionPeriodUnitMonth
import com.revenuecat.purchases.kn.core.RCSubscriptionPeriodUnitYear
import com.revenuecat.purchases.kn.core.additional.InstallmentsInfoPrices
import platform.Foundation.NSDecimalNumber
import platform.darwin.NSObject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class InstallmentsInfoTest {

    @Test
    fun `maps every field of iOS installments info`() {
        val installmentsInfo = iosInstallmentsInfo(billingPlanType = RCBillingPlanType.RCMonthly())
            .toInstallmentsInfo(currencyCode = "USD")

        assertEquals(12, installmentsInfo.commitmentPaymentsCount)
        assertEquals(1, installmentsInfo.commitmentInstallmentPeriod?.value)
        assertEquals(PeriodUnit.MONTH, installmentsInfo.commitmentInstallmentPeriod?.unit)
        assertEquals("$9.99", installmentsInfo.installmentBillingPrice?.formatted)
        assertEquals(9_990_000L, installmentsInfo.installmentBillingPrice?.amountMicros)
        assertEquals("USD", installmentsInfo.installmentBillingPrice?.currencyCode)
        assertEquals(1, installmentsInfo.commitmentTotalPeriod?.value)
        assertEquals(PeriodUnit.YEAR, installmentsInfo.commitmentTotalPeriod?.unit)
        assertEquals("$119.88", installmentsInfo.commitmentTotalPrice?.formatted)
        assertEquals(119_880_000L, installmentsInfo.commitmentTotalPrice?.amountMicros)
        assertEquals("USD", installmentsInfo.commitmentTotalPrice?.currencyCode)
        assertEquals(BillingPlanType.MONTHLY, installmentsInfo.billingPlanType)
    }

    @Test
    fun `renewal commitment payments count matches the commitment installments count`() {
        val installmentsInfo = iosInstallmentsInfo(billingPlanType = RCBillingPlanType.RCMonthly())
            .toInstallmentsInfo(currencyCode = "USD")

        assertEquals(12, installmentsInfo.renewalCommitmentPaymentsCount)
    }

    @Test
    fun `maps up front billing plan type`() {
        val installmentsInfo = iosInstallmentsInfo(billingPlanType = RCBillingPlanType.RCUpFront())
            .toInstallmentsInfo(currencyCode = "USD")

        assertEquals(BillingPlanType.UP_FRONT, installmentsInfo.billingPlanType)
    }

    @Test
    fun `price helpers return the decimal prices of installments info`() {
        val installmentsInfo = iosInstallmentsInfo(billingPlanType = RCBillingPlanType.RCMonthly())

        assertEquals(
            NSDecimalNumber(string = "9.99"),
            InstallmentsInfoPrices.installmentBillingPrice(installmentsInfo),
        )
        assertEquals(
            NSDecimalNumber(string = "119.88"),
            InstallmentsInfoPrices.commitmentTotalPrice(installmentsInfo),
        )
    }

    @Test
    fun `price helpers return null for objects that are not installments info`() {
        assertNull(InstallmentsInfoPrices.installmentBillingPrice(NSObject()))
        assertNull(InstallmentsInfoPrices.commitmentTotalPrice(NSObject()))
    }

    private fun iosInstallmentsInfo(billingPlanType: RCBillingPlanType): RCInstallmentsInfo =
        InstallmentsInfoTestFactory.installmentsInfoWithCommitmentInstallmentsCount(
            commitmentInstallmentsCount = 12,
            commitmentInstallmentPeriodValue = 1,
            commitmentInstallmentPeriodUnit = RCSubscriptionPeriodUnitMonth,
            installmentBillingPrice = NSDecimalNumber(string = "9.99"),
            installmentBillingDisplayPrice = "$9.99",
            commitmentTotalPeriodValue = 1,
            commitmentTotalPeriodUnit = RCSubscriptionPeriodUnitYear,
            commitmentTotalPrice = NSDecimalNumber(string = "119.88"),
            commitmentTotalDisplayPrice = "$119.88",
            billingPlanType = billingPlanType,
        ) as RCInstallmentsInfo
}
