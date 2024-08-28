package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.Price
import com.revenuecat.purchases.models.Price as AndroidPrice

internal fun AndroidPrice.toPrice(): Price = Price(formatted, amountMicros, currencyCode)
