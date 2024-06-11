package com.revenuecat.purchases.kmp.i18n

/**
 * Minimal representation of a locale.
 */
public class Locale private constructor(
    public val languageCode: String,
    public val countryCode: String
) {

    public companion object {
        public val Default: Locale = defaultLocale()

        /**
         * Creates a new Locale instance.
         *
         * @param languageCode A 2-letter [ISO 639](https://en.wikipedia.org/wiki/ISO_639) language
         * code.
         * @param countryCode A 0, 2 or 3-letter
         * [ISO 3166-1](https://en.wikipedia.org/wiki/ISO_3166-1) country code.
         */
        public fun create(languageCode: String, countryCode: String): Locale =
            Locale(
                languageCode = languageCode.lowercase(),
                countryCode = countryCode.lowercase()
            )
    }

    init {
        require(languageCode.length == 2) {
            "Expected a 2-letter language code, but got: $languageCode"
        }
        require(countryCode.isEmpty() || countryCode.length == 2 || countryCode.length == 3) {
            "Expected a 0, 2 or 3-letter country code, but got: $countryCode"
        }
    }
}

internal expect fun defaultLocale(): Locale
