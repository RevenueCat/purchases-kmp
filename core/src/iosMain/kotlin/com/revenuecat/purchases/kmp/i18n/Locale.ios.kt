package com.revenuecat.purchases.kmp.i18n

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

public fun Locale.toNsLocale(): NSLocale =
    NSLocale(localeIdentifier = "${languageCode}-${countryCode}")

public fun NSLocale.toLocale(): Locale =
    Locale.create(
        languageCode = languageCode,
        countryCode = countryCode.orEmpty(),
    )

internal actual fun defaultLocale(): Locale =
    NSLocale.currentLocale.toLocale()
