package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.ExperimentalRevenueCatApi
import com.revenuecat.purchases.kmp.InternalRevenueCatApi
import com.revenuecat.purchases.kmp.models.RewardedAdTrackingMetadata
import com.revenuecat.purchases.kmp.models.RewardVerificationResult
import com.revenuecat.purchases.kmp.models.RewardVerificationToken
import com.revenuecat.purchases.kmp.models.VerifiedReward
import com.revenuecat.purchases.kn.core.additional.RewardedAdTrackingMetadata as IosRewardedAdTrackingMetadata
import com.revenuecat.purchases.kn.core.additional.RewardVerificationResult as IosRewardVerificationResult
import com.revenuecat.purchases.kn.core.additional.RewardVerificationToken as IosRewardVerificationToken
import com.revenuecat.purchases.kn.core.additional.VerifiedReward as IosVerifiedReward
import com.revenuecat.purchases.kn.core.additional.VerifiedRewardKindEntitlement
import com.revenuecat.purchases.kn.core.additional.VerifiedRewardKindNoReward
import com.revenuecat.purchases.kn.core.additional.VerifiedRewardKindVirtualCurrency

@ExperimentalRevenueCatApi
public fun IosRewardVerificationToken.toKmp(): RewardVerificationToken =
    RewardVerificationToken(
        customData = customData(),
        clientTransactionId = clientTransactionId(),
        appUserID = appUserID(),
    )

@ExperimentalRevenueCatApi
public fun IosRewardVerificationResult.toKmp(): RewardVerificationResult {
    @Suppress("UNCHECKED_CAST")
    return RewardVerificationResult(
        verifiedReward = verifiedReward()?.toKmp(),
        moreRewards = (moreRewards() as List<IosVerifiedReward>).map { it.toKmp() },
        failed = failed(),
    )
}

@ExperimentalRevenueCatApi
@OptIn(InternalRevenueCatApi::class)
public fun RewardedAdTrackingMetadata.toIos(): IosRewardedAdTrackingMetadata =
    IosRewardedAdTrackingMetadata(
        networkName = networkName,
        mediatorName = mediatorName.value,
        adFormat = adFormat.value,
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
    )

@ExperimentalRevenueCatApi
internal fun IosVerifiedReward.toKmp(): VerifiedReward =
    when (kind()) {
        VerifiedRewardKindVirtualCurrency -> VerifiedReward.VirtualCurrency(
            code = virtualCurrencyCode()!!,
            amount = virtualCurrencyAmount().toInt(),
        )
        VerifiedRewardKindEntitlement -> VerifiedReward.Entitlement(
            identifier = entitlementIdentifier()!!,
            expiresAtMillis = entitlementExpiresAtMillis(),
        )
        VerifiedRewardKindNoReward -> VerifiedReward.NoReward
        else -> VerifiedReward.UnsupportedReward
    }
