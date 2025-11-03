package com.revenuecat.purchases.kmp.apitester

import androidx.startup.Initializer
import com.revenuecat.purchases.kmp.di.PurchasesInitializer

private class PurchasesInitializerAPI {

    fun check() {
        val initializer: Initializer<Unit> = PurchasesInitializer()
    }

}
