package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.mappings.ktx.toEpochMilliseconds
import com.revenuecat.purchases.kmp.models.SubscriptionInfo
import com.revenuecat.purchases.kmp.models.SubscriptionPrice
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterStyle
import platform.Foundation.NSLocale
import platform.Foundation.NSDecimalNumber
import cocoapods.PurchasesHybridCommon.RCSubscriptionInfo as IosSubscriptionInfo

public fun IosSubscriptionInfo.toSubscriptionInfo(): SubscriptionInfo {
    return SubscriptionInfo(
        productIdentifier = productIdentifier(),
        purchaseDateMillis = purchaseDate().toEpochMilliseconds(),
        originalPurchaseDateMillis = originalPurchaseDate()?.toEpochMilliseconds(),
        expiresDateMillis = expiresDate()?.toEpochMilliseconds(),
        store = store().toStore(),
        isSandbox = isSandbox(),
        unsubscribeDetectedAtMillis = unsubscribeDetectedAt()?.toEpochMilliseconds(),
        billingIssuesDetectedAtMillis = billingIssuesDetectedAt()?.toEpochMilliseconds(),
        gracePeriodExpiresDateMillis = gracePeriodExpiresDate()?.toEpochMilliseconds(),
        ownershipType = ownershipType().toOwnershipType(),
        periodType = periodType().toPeriodType(),
        refundedAtMillis = refundedAt()?.toEpochMilliseconds(),
        storeTransactionId = storeTransactionId(),
        autoResumeDateMillis = null, // Not available in iOS SubscriptionInfo
        price = price()?.let { iosPrice ->
            SubscriptionPrice(
                currency = iosPrice.currency(),
                amount = iosPrice.amount(),
                formatted = formatPrice(iosPrice.amount(), iosPrice.currency())
            )
        },
        productPlanIdentifier = null, // Not available in iOS SubscriptionInfo
        managementUrlString = managementURL()?.absoluteString(),
        isActive = isActive(),
        willRenew = willRenew(),
    )
}

private fun formatPrice(amount: Double, currencyCode: String): String {
    val formatter = NSNumberFormatter()
    formatter.numberStyle = NSNumberFormatterStyle.NSNumberFormatterCurrencyStyle
    formatter.locale = NSLocale.autoupdatingCurrentLocale()
    formatter.currencyCode = currencyCode
    
    val formattedPrice = formatter.stringFromNumber(NSDecimalNumber.decimalNumberWithDecimal(amount.toString()))
    return formattedPrice ?: "$amount $currencyCode"
}

private fun cocoapods.PurchasesHybridCommon.RCPurchaseOwnershipType.toOwnershipType(): com.revenuecat.purchases.kmp.models.OwnershipType {
    return when (this.name()) {
        "PURCHASED" -> com.revenuecat.purchases.kmp.models.OwnershipType.PURCHASED
        "FAMILY_SHARED" -> com.revenuecat.purchases.kmp.models.OwnershipType.FAMILY_SHARED
        else -> com.revenuecat.purchases.kmp.models.OwnershipType.UNKNOWN
    }
}

private fun cocoapods.PurchasesHybridCommon.RCPeriodType.toPeriodType(): com.revenuecat.purchases.kmp.models.PeriodType {
    return when (this.name()) {
        "NORMAL" -> com.revenuecat.purchases.kmp.models.PeriodType.NORMAL
        "INTRO" -> com.revenuecat.purchases.kmp.models.PeriodType.INTRO
        "TRIAL" -> com.revenuecat.purchases.kmp.models.PeriodType.TRIAL
        "PREPAID" -> com.revenuecat.purchases.kmp.models.PeriodType.PREPAID
        else -> com.revenuecat.purchases.kmp.models.PeriodType.NORMAL
    }
}