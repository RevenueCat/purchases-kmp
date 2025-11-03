package com.revenuecat.purchases.kmp.apitester

import androidx.startup.Initializer
import com.revenuecat.purchases.kmp.di.PurchasesApplicationProvider

private class PurchasesApplicationProviderAPI {

    fun check() {
        val initializer: Initializer<Unit> = PurchasesApplicationProvider()
    }

}
