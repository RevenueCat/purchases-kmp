package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.ExperimentalPreviewRevenueCatPurchasesAPI
import com.revenuecat.purchases.kmp.models.RewardedAdTrackingMetadata
import com.revenuecat.purchases.kmp.models.RewardVerificationResult
import com.revenuecat.purchases.kmp.models.RewardVerificationToken
import com.revenuecat.purchases.kmp.models.VerifiedReward
import com.revenuecat.purchases.ads.rewardverification.RewardedAdTrackingMetadata as AndroidRewardedAdTrackingMetadata
import com.revenuecat.purchases.ads.rewardverification.RewardVerificationResult as AndroidRewardVerificationResult
import com.revenuecat.purchases.ads.rewardverification.RewardVerificationToken as AndroidRewardVerificationToken
import com.revenuecat.purchases.ads.rewardverification.VerifiedReward as AndroidVerifiedReward

@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AndroidRewardVerificationToken.toKmp(): RewardVerificationToken =
    RewardVerificationToken(
        customData = customData,
        clientTransactionId = clientTransactionId,
        appUserID = appUserID,
    )

@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun AndroidRewardVerificationResult.toKmp(): RewardVerificationResult =
    RewardVerificationResult(
        verifiedReward = verifiedReward?.toKmp(),
        moreRewards = moreRewards.map { it.toKmp() },
        failed = failed,
    )

@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
public fun RewardedAdTrackingMetadata.toAndroid(): AndroidRewardedAdTrackingMetadata =
    AndroidRewardedAdTrackingMetadata(
        networkName = networkName,
        mediatorName = mediatorName.toAndroid(),
        adFormat = adFormat.toAndroid(),
        placement = placement,
        adUnitId = adUnitId,
        impressionId = impressionId,
    )

@OptIn(ExperimentalPreviewRevenueCatPurchasesAPI::class)
internal fun AndroidVerifiedReward.toKmp(): VerifiedReward =
    when (this) {
        is AndroidVerifiedReward.VirtualCurrency -> VerifiedReward.VirtualCurrency(code = code, amount = amount)
        is AndroidVerifiedReward.Entitlement -> VerifiedReward.Entitlement(
            identifier = identifier,
            expiresAtMillis = expiresAt.time,
        )
        AndroidVerifiedReward.NoReward -> VerifiedReward.NoReward
        else -> VerifiedReward.UnsupportedReward
    }
