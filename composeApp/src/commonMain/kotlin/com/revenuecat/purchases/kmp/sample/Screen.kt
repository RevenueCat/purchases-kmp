package com.revenuecat.purchases.kmp.sample

import com.revenuecat.purchases.kmp.Offering

sealed interface Screen {

    data object Main : Screen
    data class Paywall(val offering: Offering?) : Screen
    data class PaywallFooter(val offering: Offering?) : Screen

}
