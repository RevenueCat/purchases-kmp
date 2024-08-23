package com.revenuecat.purchases.kmp

import com.revenuecat.purchases.ProductType as AndroidProductType

internal fun AndroidProductType.toProductType(): ProductType {
    return when (this) {
        AndroidProductType.SUBS -> ProductType.SUBS
        AndroidProductType.INAPP -> ProductType.INAPP
        AndroidProductType.UNKNOWN -> ProductType.UNKNOWN
    }
}
