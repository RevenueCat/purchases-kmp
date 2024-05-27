@file:Suppress("EXTENSION_SHADOWED_BY_MEMBER")

package io.shortway.kobankat.models

import cocoapods.PurchasesHybridCommon.RCStoreProduct
import io.shortway.kobankat.PresentedOfferingContext
import io.shortway.kobankat.ProductType
import io.shortway.kobankat.i18n.Locale
import io.shortway.kobankat.i18n.toNsLocale
import io.shortway.kobankat.toProductType
import kotlinx.cinterop.convert
import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSNumber

private const val DAYS_PER_WEEK = 7
private const val DAYS_PER_MONTH = 32
private const val DAYS_PER_YEAR = 365
private const val WEEKS_PER_MONTH = 4
private const val WEEKS_PER_YEAR = 52
private const val MONTHS_PER_YEAR = 12

public actual typealias StoreProduct = RCStoreProduct

public actual val StoreProduct.id: String
    get() = productIdentifier()
public actual val StoreProduct.type: ProductType
    get() = productType().toProductType()
public actual val StoreProduct.price: Price
    get() = toPrice()
public actual val StoreProduct.title: String
    get() = localizedTitle()
public actual val StoreProduct.description: String?
    get() = localizedDescription()
public actual val StoreProduct.period: Period?
    get() = subscriptionPeriod()
public actual val StoreProduct.subscriptionOptions: SubscriptionOptions?
    get() = null
public actual val StoreProduct.defaultOption: SubscriptionOption?
    get() = null
public actual val StoreProduct.discounts: List<StoreProductDiscount>
    get() = discounts().map { it as StoreProductDiscount }
public actual val StoreProduct.introductoryDiscount: StoreProductDiscount?
    get() = introductoryDiscount()
public actual val StoreProduct.purchasingData: PurchasingData
    get() = toPurchasingData()
public actual val StoreProduct.presentedOfferingContext: PresentedOfferingContext?
    get() = null // FIXME No public API through ObjC in PHC?

public actual fun StoreProduct.pricePerWeek(locale: Locale): Price? =
    period?.let { period ->
        adjustAmountToSinglePeriodUnit(
            amountMicros = price.amountMicros,
            originalPeriod = period,
            newPeriodUnit = PeriodUnit.WEEK
        )?.let { buildPriceOrNull(it, locale) }
    }

public actual fun StoreProduct.pricePerMonth(locale: Locale): Price? =
    period?.let { period ->
        adjustAmountToSinglePeriodUnit(
            amountMicros = price.amountMicros,
            originalPeriod = period,
            newPeriodUnit = PeriodUnit.MONTH
        )?.let { buildPriceOrNull(it, locale) }
    }

public actual fun StoreProduct.pricePerYear(locale: Locale): Price? =
    period?.let { period ->
        adjustAmountToSinglePeriodUnit(
            amountMicros = price.amountMicros,
            originalPeriod = period,
            newPeriodUnit = PeriodUnit.YEAR
        )?.let { buildPriceOrNull(it, locale) }
    }

public actual fun StoreProduct.formattedPricePerMonth(locale: Locale): String? =
    pricePerMonth(locale)?.formatted

private fun adjustAmountToSinglePeriodUnit(
    amountMicros: Long,
    originalPeriod: Period,
    newPeriodUnit: PeriodUnit
): Long? {
    val singlePeriodAmount = amountMicros / originalPeriod.value

    val adjust: (Long) -> Long? = when (newPeriodUnit) {
        PeriodUnit.DAY -> when (originalPeriod.unit) {
            PeriodUnit.DAY -> { { it } }
            PeriodUnit.WEEK -> { { it / DAYS_PER_WEEK } }
            PeriodUnit.MONTH -> { { it / DAYS_PER_MONTH } }
            PeriodUnit.YEAR -> { { it / DAYS_PER_YEAR } }
            PeriodUnit.UNKNOWN -> { { null } }
        }

        PeriodUnit.WEEK -> when (originalPeriod.unit) {
            PeriodUnit.DAY -> { { it * DAYS_PER_WEEK } }
            PeriodUnit.WEEK -> { { it } }
            PeriodUnit.MONTH -> { { it / WEEKS_PER_MONTH } }
            PeriodUnit.YEAR -> { { it / WEEKS_PER_YEAR } }
            PeriodUnit.UNKNOWN -> { { null } }
        }

        PeriodUnit.MONTH -> when (originalPeriod.unit) {
            PeriodUnit.DAY -> { { it * DAYS_PER_MONTH } }
            PeriodUnit.WEEK -> { { it * WEEKS_PER_MONTH } }
            PeriodUnit.MONTH -> { { it } }
            PeriodUnit.YEAR -> { { it / MONTHS_PER_YEAR } }
            PeriodUnit.UNKNOWN -> { { null } }
        }

        PeriodUnit.YEAR -> when (originalPeriod.unit) {
            PeriodUnit.DAY -> { { it * DAYS_PER_YEAR } }
            PeriodUnit.WEEK -> { { it * WEEKS_PER_YEAR } }
            PeriodUnit.MONTH -> { { it * MONTHS_PER_YEAR } }
            PeriodUnit.YEAR -> { { it } }
            PeriodUnit.UNKNOWN -> { { null } }
        }

        PeriodUnit.UNKNOWN -> { { null } }
    }

    return adjust(singlePeriodAmount)
}

private fun RCStoreProduct.buildPriceOrNull(
    amountMicros: Long,
    locale: Locale
): Price? =
    priceFormatter()?.run {
        setLocale(locale.toNsLocale())
        val decimal = amountMicros.toDouble() / 1_000_000
        stringFromNumber(NSDecimalNumber(double = decimal))
    }?.let { formatted ->
        Price(
            formatted = formatted,
            amountMicros = amountMicros,
            currencyCode = currencyCodeOrUsd()
        )
    }
