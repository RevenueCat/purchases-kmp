package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.models.Offering
import com.revenuecat.purchases.kmp.ui.revenuecatui.CustomVariableValue
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallListener
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions


@Suppress("unused", "UNUSED_VARIABLE", "RedundantExplicitType")
private class PaywallOptionsAPI {
    fun check(offering: Offering?, listener: PaywallListener?) {
        val options: PaywallOptions = PaywallOptions(
            dismissRequest = {}
        ) {
            this.offering = offering
            this.shouldDisplayDismissButton = true
            this.listener = listener
            this.customVariables = mapOf(
                "name" to CustomVariableValue.string("John"),
                "level" to CustomVariableValue.number(5),
                "isPremium" to CustomVariableValue.boolean(true),
            )
        }
        val listener2: PaywallListener? = options.listener
        val dismissRequest: () -> Unit = options.dismissRequest
        val customVariables: Map<String, CustomVariableValue> = options.customVariables
    }
}
