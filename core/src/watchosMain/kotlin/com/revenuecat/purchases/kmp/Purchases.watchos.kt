package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.models.StoreMessageType
import com.revenuecat.purchases.kn.core.RCPurchases as IosPurchases

internal actual fun IosPurchases.presentCodeRedemptionSheetIfAvailable() {
    Purchases.logHandler.d(
        tag = "Purchases",
        msg = "`presentCodeRedemptionSheet()` is not available on watchOS."
    )
}

internal actual fun IosPurchases.showStoreMessagesIfAvailable(
    messageTypes: List<StoreMessageType>,
) {
    Purchases.logHandler.d(
        tag = "Purchases",
        msg = "`showInAppMessagesIfNeeded()` is not available on watchOS."
    )
}
