package io.shortway.kobankat.models

import io.shortway.kobankat.ReplacementMode

public actual enum class GoogleReplacementMode: ReplacementMode {
    WITHOUT_PRORATION,
    WITH_TIME_PRORATION,
    CHARGE_FULL_PRICE,
    CHARGE_PRORATED_PRICE,
}