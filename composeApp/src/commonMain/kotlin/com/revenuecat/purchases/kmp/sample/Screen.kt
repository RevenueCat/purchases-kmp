package com.revenuecat.purchases.kmp.sample

import com.revenuecat.purchases.kmp.models.Offering

sealed interface Screen {

    data object Main : Screen
    data class Paywall(val offering: Offering?) : Screen
    data class PaywallFooter(val offering: Offering?) : Screen
    data object WinBackTesting : Screen
    data object CustomerCenter : Screen
    data object VirtualCurrencyTesting : Screen
    data object AdTracking : Screen

}
