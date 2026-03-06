package com.revenuecat.automatedsdktests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val offeringId = intent.getStringExtra("offering_id")
        setContent { App(offeringId) }
    }
}
