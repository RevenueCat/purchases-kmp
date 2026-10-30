package com.revenuecat.purchases.kmp.mappings

import com.revenuecat.purchases.kn.core.RCStoreProduct
import com.revenuecat.purchases.kn.core.create
import kotlinx.cinterop.BetaInteropApi
import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSLocale
import platform.StoreKit.SKProduct

internal class FakeSKProduct : SKProduct() {
    override fun productIdentifier(): String = "monthly"
    override fun localizedTitle(): String = "Monthly"
    override fun localizedDescription(): String = "Monthly subscription"
    override fun price(): NSDecimalNumber = NSDecimalNumber(string = "9.99")
    override fun priceLocale(): NSLocale = NSLocale(localeIdentifier = "en_US")
}

/**
 * RevenueCat's generated Objective-C header only forward-declares `SKProduct`, so cinterop types
 * `sk1Product` as `objcnames.classes.SKProduct` rather than `platform.StoreKit.SKProduct`. Both refer
 * to the same Objective-C class, so the cast always succeeds at runtime.
 */
@OptIn(BetaInteropApi::class)
internal fun SKProduct.toRCStoreProduct(): RCStoreProduct =
    RCStoreProduct.create(sk1Product = this as objcnames.classes.SKProduct)
