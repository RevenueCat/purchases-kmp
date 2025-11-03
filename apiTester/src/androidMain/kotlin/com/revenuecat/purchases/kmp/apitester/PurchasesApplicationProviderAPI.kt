package com.revenuecat.purchases.kmp.apitester

import androidx.startup.Initializer
import com.revenuecat.purchases.kmp.PurchasesApplicationProvider

private class PurchasesApplicationProviderAPI {

    fun check() {
        val initializer: Initializer<Unit> = PurchasesApplicationProvider()
    }

}
