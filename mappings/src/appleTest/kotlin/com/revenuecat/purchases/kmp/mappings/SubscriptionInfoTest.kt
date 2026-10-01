package com.revenuecat.purchases.kmp.mappings

import kotlinx.cinterop.convert
import platform.Foundation.NSDate
import platform.Foundation.NSMutableDictionary
import platform.Foundation.NSNumber
import platform.Foundation.setValue
import kotlin.test.Test
import kotlin.test.assertEquals

class SubscriptionInfoTest {

    @Test
    fun `price amountMicros is not one micro short for amounts without an exact double`() {
        assertEquals(990_000L, subscriptionInfo(amount = 0.99).toSubscriptionInfo().price?.amountMicros)
        assertEquals(2_050_000L, subscriptionInfo(amount = 2.05).toSubscriptionInfo().price?.amountMicros)
        assertEquals(8_200_000L, subscriptionInfo(amount = 8.2).toSubscriptionInfo().price?.amountMicros)
        assertEquals(64_990_000L, subscriptionInfo(amount = 64.99).toSubscriptionInfo().price?.amountMicros)
        assertEquals(1_149_990_000L, subscriptionInfo(amount = 1149.99).toSubscriptionInfo().price?.amountMicros)
    }

    private fun subscriptionInfo(amount: Double): NSMutableDictionary =
        NSMutableDictionary().apply {
            setValue("monthly", forKey = "productIdentifier")
            setValue(NSDate(), forKey = "purchaseDate")
            setValue(NSNumber(integer = 0.convert()), forKey = "store")
            setValue(NSNumber(bool = false), forKey = "isSandbox")
            setValue(NSNumber(integer = 0.convert()), forKey = "ownershipType")
            setValue(NSNumber(integer = 0.convert()), forKey = "periodType")
            setValue(NSNumber(bool = true), forKey = "isActive")
            setValue(NSNumber(bool = true), forKey = "willRenew")
            setValue(
                NSMutableDictionary().apply {
                    setValue("USD", forKey = "currency")
                    setValue(NSNumber(double = amount), forKey = "amount")
                    setValue("$$amount", forKey = "formatted")
                },
                forKey = "price",
            )
        }
}
