package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.ExperimentalPreviewRevenueCatPurchasesAPI
import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdFormat
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdMediatorName
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData
import com.revenuecat.purchases.kmp.models.AdRevenuePrecision
import com.revenuecat.purchases.ads.events.types.AdDisplayedData as AndroidAdDisplayedData
import com.revenuecat.purchases.ads.events.types.AdFailedToLoadData as AndroidAdFailedToLoadData
import com.revenuecat.purchases.ads.events.types.AdFormat as AndroidAdFormat
import com.revenuecat.purchases.ads.events.types.AdLoadedData as AndroidAdLoadedData
import com.revenuecat.purchases.ads.events.types.AdMediatorName as AndroidAdMediatorName
import com.revenuecat.purchases.ads.events.types.AdOpenedData as AndroidAdOpenedData
import com.revenuecat.purchases.ads.events.types.AdRevenueData as AndroidAdRevenueData
import com.revenuecat.purchases.ads.events.types.AdRevenuePrecision as AndroidAdRevenuePrecision

@ExperimentalRevenueCatApi
@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AdMediatorName.toAndroid(): AndroidAdMediatorName {
    return AndroidAdMediatorName.fromString(this.value)
}

@ExperimentalRevenueCatApi
@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AdRevenuePrecision.toAndroid(): AndroidAdRevenuePrecision {
    return AndroidAdRevenuePrecision.fromString(this.value)
}

@ExperimentalRevenueCatApi
@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AdFormat.toAndroid(): AndroidAdFormat {
    return AndroidAdFormat.fromString(this.value)
}

@ExperimentalRevenueCatApi
@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AdDisplayedData.toAndroid(): AndroidAdDisplayedData {
    return AndroidAdDisplayedData(
        networkName = networkName,
        mediatorName = mediatorName.toAndroid(),
        adFormat = adFormat.toAndroid(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
    )
}

@ExperimentalRevenueCatApi
@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AdOpenedData.toAndroid(): AndroidAdOpenedData {
    return AndroidAdOpenedData(
        networkName = networkName,
        mediatorName = mediatorName.toAndroid(),
        adFormat = adFormat.toAndroid(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
    )
}

@ExperimentalRevenueCatApi
@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AdRevenueData.toAndroid(): AndroidAdRevenueData {
    return AndroidAdRevenueData(
        networkName = networkName,
        mediatorName = mediatorName.toAndroid(),
        adFormat = adFormat.toAndroid(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
        revenueMicros = revenueMicros,
        currency = currency,
        precision = precision.toAndroid(),
    )
}

@ExperimentalRevenueCatApi
@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AdLoadedData.toAndroid(): AndroidAdLoadedData {
    return AndroidAdLoadedData(
        networkName = networkName,
        mediatorName = mediatorName.toAndroid(),
        adFormat = adFormat.toAndroid(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
    )
}

@ExperimentalRevenueCatApi
@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AdFailedToLoadData.toAndroid(): AndroidAdFailedToLoadData {
    return AndroidAdFailedToLoadData(
        networkName = networkName,
        mediatorName = mediatorName.toAndroid(),
        adFormat = adFormat.toAndroid(),
        placement = placement,
        adUnitId = adUnitId,
        mediatorErrorCode = mediatorErrorCode,
    )
}
