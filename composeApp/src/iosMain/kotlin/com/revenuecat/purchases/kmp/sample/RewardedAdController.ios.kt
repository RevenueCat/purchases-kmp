package com.revenuecat.purchases.kmp.sample

import androidx.compose.runtime.Composable
import com.revenuecat.purchases.kmp.sample.ads.gma.GADFullScreenContentDelegateProtocol
import com.revenuecat.purchases.kmp.sample.ads.gma.GADFullScreenPresentingAdProtocol
import com.revenuecat.purchases.kmp.sample.ads.gma.GADMobileAds
import com.revenuecat.purchases.kmp.sample.ads.gma.GADRequest
import com.revenuecat.purchases.kmp.sample.ads.gma.GADRewardedInterstitialAd
import com.revenuecat.purchases.kmp.sample.ads.gma.GADServerSideVerificationOptions
import platform.darwin.NSObject

private var didStartGoogleMobileAds = false

private fun ensureGoogleMobileAdsStarted() {
    if (didStartGoogleMobileAds) return
    didStartGoogleMobileAds = true
    GADMobileAds.sharedInstance.startWithCompletionHandler(null)
}

actual class RewardedAdController {

    private var rewardedAd: GADRewardedInterstitialAd? = null

    actual fun loadAd(
        adUnitId: String,
        presentingHost: Any?,
        onLoaded: (responseId: String) -> Unit,
        onFailedToLoad: (String) -> Unit,
    ) {
        ensureGoogleMobileAdsStarted()
        GADRewardedInterstitialAd.loadWithAdUnitID(adUnitId, GADRequest()) { ad, error ->
            if (error != null) {
                onFailedToLoad(error.localizedDescription)
                return@loadWithAdUnitID
            }
            rewardedAd = ad
            onLoaded(ad?.responseInfo?.responseIdentifier ?: "")
        }
    }

    actual fun setServerSideVerificationOptions(userId: String, customData: String) {
        val options = GADServerSideVerificationOptions()
        options.userIdentifier = userId
        options.customRewardString = customData
        rewardedAd?.serverSideVerificationOptions = options
    }

    actual fun present(presentingHost: Any?, onUserEarnedReward: () -> Unit, onDismissed: () -> Unit) {
        val ad = rewardedAd
        if (ad == null) {
            onDismissed()
            return
        }
        rewardedAd = null
        ad.fullScreenContentDelegate = object : NSObject(), GADFullScreenContentDelegateProtocol {
            override fun adDidDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                onDismissed()
            }
        }
        ad.presentFromRootViewController(viewController = null) {
            onUserEarnedReward()
        }
    }
}

@Composable
actual fun rememberAdPresentingHost(): Any? = null

actual val rewardedAdUnitId: String = "ca-app-pub-3940256099942544/6978759866"
