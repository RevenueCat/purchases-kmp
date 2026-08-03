package com.revenuecat.purchases.kmp.sample

import androidx.compose.runtime.Composable

/**
 * Local-only manual test harness for a real rewarded-ad SSV flow. iOS delegates to GoogleMobileAds
 * directly via cinterop; Android delegates to the play-services-ads SDK.
 */
expect class RewardedAdController() {
    fun loadAd(
        adUnitId: String,
        presentingHost: Any?,
        onLoaded: (responseId: String) -> Unit,
        onFailedToLoad: (String) -> Unit,
    )
    fun setServerSideVerificationOptions(userId: String, customData: String)
    fun present(presentingHost: Any?, onUserEarnedReward: () -> Unit, onDismissed: () -> Unit)
}

/**
 * The platform object [RewardedAdController] needs to load/show a full-screen ad: an `Activity` on
 * Android, unused on iOS (which resolves its own root view controller and doesn't need a `Context`
 * to load an ad either).
 */
@Composable
expect fun rememberAdPresentingHost(): Any?

// Google's official test rewarded-ad unit for each platform. Always fills with a test ad and is
// safe to commit.
expect val rewardedAdUnitId: String
