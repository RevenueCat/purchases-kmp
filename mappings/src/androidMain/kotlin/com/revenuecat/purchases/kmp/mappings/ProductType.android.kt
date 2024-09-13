package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.ProductType
import com.revenuecat.purchases.ProductType as AndroidProductType

internal fun AndroidProductType.toProductType(): ProductType {
    return when (this) {
        AndroidProductType.SUBS -> ProductType.SUBS
        AndroidProductType.INAPP -> ProductType.INAPP
        AndroidProductType.UNKNOWN -> ProductType.UNKNOWN
    }
}
