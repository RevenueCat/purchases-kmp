package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdFormat
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdMediatorName
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData
import com.revenuecat.purchases.kmp.models.AdRevenuePrecision
import platform.Foundation.NSNumber
import swiftPMImport.com.revenuecat.purchases.kn.core.RCAdDisplayed
import swiftPMImport.com.revenuecat.purchases.kn.core.RCAdFailedToLoad
import swiftPMImport.com.revenuecat.purchases.kn.core.RCAdFormat
import swiftPMImport.com.revenuecat.purchases.kn.core.RCAdLoaded
import swiftPMImport.com.revenuecat.purchases.kn.core.RCAdOpened
import swiftPMImport.com.revenuecat.purchases.kn.core.RCAdRevenue
import swiftPMImport.com.revenuecat.purchases.kn.core.RCAdRevenuePrecision
import swiftPMImport.com.revenuecat.purchases.kn.core.RCMediatorName

@ExperimentalRevenueCatApi
public fun AdMediatorName.toIos(): RCMediatorName =
    RCMediatorName(rawValue = value)

@ExperimentalRevenueCatApi
public fun AdRevenuePrecision.toIos(): RCAdRevenuePrecision =
    RCAdRevenuePrecision(rawValue = value)

@ExperimentalRevenueCatApi
public fun AdFormat.toIos(): RCAdFormat =
    RCAdFormat(rawValue = value)

@ExperimentalRevenueCatApi
public fun AdDisplayedData.toIos(): RCAdDisplayed {
    return RCAdDisplayed(
        networkName = networkName,
        mediatorName = mediatorName.toIos(),
        adFormat = adFormat.toIos(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
    )
}

@ExperimentalRevenueCatApi
public fun AdOpenedData.toIos(): RCAdOpened {
    return RCAdOpened(
        networkName = networkName,
        mediatorName = mediatorName.toIos(),
        adFormat = adFormat.toIos(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
    )
}

@ExperimentalRevenueCatApi
public fun AdRevenueData.toIos(): RCAdRevenue {
    return RCAdRevenue(
        networkName = networkName,
        mediatorName = mediatorName.toIos(),
        adFormat = adFormat.toIos(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
        revenueMicros = revenueMicros,
        currency = currency,
        precision = precision.toIos(),
    )
}

@ExperimentalRevenueCatApi
public fun AdLoadedData.toIos(): RCAdLoaded {
    return RCAdLoaded(
        networkName = networkName,
        mediatorName = mediatorName.toIos(),
        adFormat = adFormat.toIos(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
    )
}

@ExperimentalRevenueCatApi
public fun AdFailedToLoadData.toIos(): RCAdFailedToLoad {
    return RCAdFailedToLoad(
        mediatorName = mediatorName.toIos(),
        adFormat = adFormat.toIos(),
        placement = placement,
        adUnitId = adUnitId,
        mediatorErrorCode = mediatorErrorCode?.let { NSNumber(it) },
    )
}
