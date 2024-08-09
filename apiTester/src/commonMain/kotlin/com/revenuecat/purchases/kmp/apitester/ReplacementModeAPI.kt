package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.ReplacementMode
import com.revenuecat.purchases.kmp.models.GoogleReplacementMode

@Suppress("unused", "UNUSED_VARIABLE")
private class ReplacementModeAPI {
    fun check(mode: ReplacementMode, googleReplacementMode: GoogleReplacementMode) {
        val name: String = mode.name
        val googleMode: ReplacementMode = googleReplacementMode
        val newMode = object : ReplacementMode {
            override val name: String = "newMode"
        }
    }
}
