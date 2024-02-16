package io.shortway.kobankat.i18n

import java.util.Locale as JvmLocale

public fun Locale.toJvmLocale(): JvmLocale =
    JvmLocale.forLanguageTag("${languageCode}-${countryCode}")

public fun JvmLocale.toLocale(): Locale =
    Locale.create(
        languageCode = language,
        countryCode = country
    )

internal actual fun defaultLocale(): Locale =
    JvmLocale.getDefault().toLocale()