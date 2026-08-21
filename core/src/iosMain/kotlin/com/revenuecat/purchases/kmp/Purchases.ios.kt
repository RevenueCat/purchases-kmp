package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.kmp.mappings.toIosStoreMessageTypes
import com.revenuecat.purchases.kmp.models.StoreMessageType
import com.revenuecat.purchases.kn.core.additional.AppleApiAvailability
import com.revenuecat.purchases.kn.core.showStoreMessagesForTypes
import com.revenuecat.purchases.kn.core.RCPurchases as IosPurchases

internal actual fun IosPurchases.presentCodeRedemptionSheetIfAvailable() {
    if (AppleApiAvailability().isCodeRedemptionSheetAPIAvailable())
        presentCodeRedemptionSheet()
    else Purchases.logHandler.d(
        tag = "Purchases",
        msg = "`presentCodeRedemptionSheet()` is only available on iOS 14.0 and up."
    )
}

internal actual fun IosPurchases.showStoreMessagesIfAvailable(
    messageTypes: List<StoreMessageType>,
) {
    showStoreMessagesForTypes(messageTypes.toIosStoreMessageTypes()) {}
}
