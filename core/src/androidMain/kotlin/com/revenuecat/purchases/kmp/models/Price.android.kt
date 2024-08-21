package com.revenuecat.purchases.kmp.models

import com.revenuecat.purchases.models.Price as AndroidPrice

internal fun AndroidPrice.toPrice(): Price = Price(formatted, amountMicros, currencyCode)