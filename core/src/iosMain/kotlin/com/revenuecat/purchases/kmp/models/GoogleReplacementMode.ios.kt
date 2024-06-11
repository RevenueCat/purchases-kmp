package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.kmp.ReplacementMode

public actual enum class GoogleReplacementMode: ReplacementMode {
    WITHOUT_PRORATION,
    WITH_TIME_PRORATION,
    CHARGE_FULL_PRICE,
    CHARGE_PRORATED_PRICE,
}
