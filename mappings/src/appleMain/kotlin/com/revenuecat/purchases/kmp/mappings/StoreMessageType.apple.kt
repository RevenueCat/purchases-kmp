package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.StoreMessageType
import com.revenuecat.purchases.kn.core.RCStoreMessageType
import com.revenuecat.purchases.kn.core.RCStoreMessageTypeBillingIssue
import com.revenuecat.purchases.kn.core.RCStoreMessageTypeGeneric
import com.revenuecat.purchases.kn.core.RCStoreMessageTypePriceIncreaseConsent
import com.revenuecat.purchases.kn.core.RCStoreMessageTypeWinBackOffer

public fun Collection<StoreMessageType>.toIosStoreMessageTypes(): Set<RCStoreMessageType> =
    mapTo(mutableSetOf()) { it.toIosStoreMessageType() }

public fun StoreMessageType.toIosStoreMessageType(): RCStoreMessageType =
    when (this) {
        StoreMessageType.GENERIC -> RCStoreMessageTypeGeneric
        StoreMessageType.BILLING_ISSUES -> RCStoreMessageTypeBillingIssue
        StoreMessageType.PRICE_INCREASE_CONSENT -> RCStoreMessageTypePriceIncreaseConsent
        StoreMessageType.WIN_BACK_OFFER -> RCStoreMessageTypeWinBackOffer
    }
