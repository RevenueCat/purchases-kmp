package com.revenuecat.purchases.kmp.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.Package
import com.revenuecat.purchases.kmp.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreTransaction
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallListener

@Composable
internal fun rememberLoggingPaywallListener() = remember {
    object : PaywallListener {
        override fun onPurchaseStarted(rcPackage: Package) {
            super.onPurchaseStarted(rcPackage)
            println("onPurchaseStarted: $rcPackage")
        }

        override fun onPurchaseCompleted(
            customerInfo: CustomerInfo,
            storeTransaction: StoreTransaction
        ) {
            super.onPurchaseCompleted(customerInfo, storeTransaction)
            println("onPurchaseCompleted: $customerInfo, $storeTransaction")
        }

        override fun onPurchaseError(error: PurchasesError) {
            super.onPurchaseError(error)
            println("onPurchaseError: $error")
        }

        override fun onPurchaseCancelled() {
            super.onPurchaseCancelled()
            println("onPurchaseCancelled")
        }

        override fun onRestoreStarted() {
            super.onRestoreStarted()
            println("onRestoreStarted")
        }

        override fun onRestoreCompleted(customerInfo: CustomerInfo) {
            super.onRestoreCompleted(customerInfo)
            println("onRestoreCompleted: $customerInfo")
        }

        override fun onRestoreError(error: PurchasesError) {
            super.onRestoreError(error)
            println("onRestoreError: $error")
        }
    }
}
