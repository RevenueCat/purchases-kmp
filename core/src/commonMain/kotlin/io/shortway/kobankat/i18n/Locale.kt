package io.shortway.kobankat.i18n

@Suppress("DataClassPrivateConstructor")
public data class Locale private constructor(val languageCode: String, val countryCode: String) {

    public companion object {
        public val Default: Locale = defaultLocale()

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