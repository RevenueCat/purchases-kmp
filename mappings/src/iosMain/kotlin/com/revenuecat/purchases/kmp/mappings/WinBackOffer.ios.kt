package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kmp.models.StoreProductDiscount
import com.revenuecat.purchases.kmp.models.WinBackOffer
import swiftPMImport.com.revenuecat.purchases.models.RCWinBackOffer as NativeIosWinBackOffer

public fun NativeIosWinBackOffer.toWinBackOffer(): WinBackOffer =
    IosWinBackOffer(this)

public fun WinBackOffer.toIosWinBackOffer(): NativeIosWinBackOffer = (this as IosWinBackOffer).wrapped

internal class IosWinBackOffer(val wrapped: NativeIosWinBackOffer) : WinBackOffer {
    override val discount: StoreProductDiscount =
        wrapped.discount().toStoreProductDiscount()
}
