package com.revenuecat.automatedsdktests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.revenuecat.maestro.e2e.API_KEY
import com.revenuecat.maestro.e2e.App
import com.revenuecat.purchases.kmp.LogLevel
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesConfiguration

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Purchases.logLevel = LogLevel.DEBUG
        Purchases.configure(PurchasesConfiguration(API_KEY))
        val testFlow = intent.getStringExtra("e2e_test_flow")
        setContent {
            App(initialTestFlow = testFlow)
        }
    }
}
