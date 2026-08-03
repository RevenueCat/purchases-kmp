package com.revenuecat.purchases.kmp.sample

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback

private var didStartGoogleMobileAds = false

private fun ensureGoogleMobileAdsStarted(activity: Activity) {
    if (didStartGoogleMobileAds) return
    didStartGoogleMobileAds = true
    MobileAds.initialize(activity)
}

actual class RewardedAdController {

    private var rewardedAd: RewardedInterstitialAd? = null

    actual fun loadAd(
        adUnitId: String,
        presentingHost: Any?,
        onLoaded: (responseId: String) -> Unit,
        onFailedToLoad: (String) -> Unit,
    ) {
        val activity = presentingHost as? Activity ?: run {
            onFailedToLoad("No Activity available to load the ad.")
            return
        }
        ensureGoogleMobileAdsStarted(activity)
        RewardedInterstitialAd.load(
            activity,
            adUnitId,
            AdRequest.Builder().build(),
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    rewardedAd = ad
                    onLoaded(ad.responseInfo.responseId.orEmpty())
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                    onFailedToLoad(error.message)
                }
            },
        )
    }

    actual fun setServerSideVerificationOptions(userId: String, customData: String) {
        val options = ServerSideVerificationOptions.Builder()
            .setUserId(userId)
            .setCustomData(customData)
            .build()
        rewardedAd?.setServerSideVerificationOptions(options)
    }

    actual fun present(presentingHost: Any?, onUserEarnedReward: () -> Unit, onDismissed: () -> Unit) {
        val ad = rewardedAd
        val activity = presentingHost as? Activity
        if (ad == null || activity == null) {
            onDismissed()
            return
        }
        rewardedAd = null
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                onDismissed()
            }
        }
        ad.show(activity) { onUserEarnedReward() }
    }
}

@Composable
actual fun rememberAdPresentingHost(): Any? = LocalContext.current as? Activity

actual val rewardedAdUnitId: String = "ca-app-pub-3940256099942544/5354046379"
