package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.InternalRevenueCatApi
import com.revenuecat.purchases.kmp.models.AdDisplayedData
import com.revenuecat.purchases.kmp.models.AdFailedToLoadData
import com.revenuecat.purchases.kmp.models.AdFormat
import com.revenuecat.purchases.kmp.models.AdLoadedData
import com.revenuecat.purchases.kmp.models.AdMediatorName
import com.revenuecat.purchases.kmp.models.AdOpenedData
import com.revenuecat.purchases.kmp.models.AdRevenueData
import com.revenuecat.purchases.kmp.models.AdRevenuePrecision
import com.revenuecat.purchases.kmp.mappings.ktx.toNSInteger
import platform.Foundation.NSNumber
import com.revenuecat.purchases.kn.core.RCAdDisplayed
import com.revenuecat.purchases.kn.core.RCAdFailedToLoad
import com.revenuecat.purchases.kn.core.RCAdFormat
import com.revenuecat.purchases.kn.core.RCAdLoaded
import com.revenuecat.purchases.kn.core.RCAdOpened
import com.revenuecat.purchases.kn.core.RCAdRevenue
import com.revenuecat.purchases.kn.core.RCAdRevenuePrecision
import com.revenuecat.purchases.kn.core.RCMediatorName

@OptIn(InternalRevenueCatApi::class)
public fun AdMediatorName.toIos(): RCMediatorName =
    RCMediatorName(rawValue = value)

@OptIn(InternalRevenueCatApi::class)
public fun AdRevenuePrecision.toIos(): RCAdRevenuePrecision =
    RCAdRevenuePrecision(rawValue = value)

public fun AdFormat.toIos(): RCAdFormat =
    RCAdFormat(rawValue = value)

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

public fun AdRevenueData.toIos(): RCAdRevenue {
    return RCAdRevenue(
        networkName = networkName,
        mediatorName = mediatorName.toIos(),
        adFormat = adFormat.toIos(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
        revenueMicros = revenueMicros.toNSInteger(),
        currency = currency,
        precision = precision.toIos(),
    )
}

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

public fun AdFailedToLoadData.toIos(): RCAdFailedToLoad {
    return RCAdFailedToLoad(
        mediatorName = mediatorName.toIos(),
        adFormat = adFormat.toIos(),
        placement = placement,
        adUnitId = adUnitId,
        mediatorErrorCode = mediatorErrorCode?.let { NSNumber(int = it) },
    )
}
