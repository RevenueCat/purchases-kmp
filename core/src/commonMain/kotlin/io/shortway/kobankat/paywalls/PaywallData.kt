package io.shortway.kobankat.paywalls

import io.shortway.kobankat.i18n.Locale

/**
 * Represents the data required to display a paywall using the `RevenueCatUI` library.
 * This data can be created and configured in the dashboard and then accessed from the `Offering/paywall`.
 *
 * @see [Paywalls Documentation](https://rev.cat/paywalls)
 */

public interface PaywallData {

    /**
     * The type of template used to display this paywall.
     */
    public val templateName: String

    /**
     * Generic configuration for any paywall.
     */
    public val config: Configuration

    /**
     * The base remote URL where assets for this paywall are stored.
     */
    public val assetBaseURL: String

    /**
     * The revision identifier for this paywall.
     */
    public val revision: Int
        get() = 0
    public val localization: Map<String, LocalizedConfiguration>

    /**
     * Returns the [Locale] and [LocalizedConfiguration] to be used based on the current locale list
     * and the available locales for this paywall.
     */
    public val localizedConfiguration: Pair<Locale, LocalizedConfiguration>



   public fun configForLocale(requiredLocale: Locale): LocalizedConfiguration?

    /**
     * Generic configuration for any paywall.
     */
    public interface Configuration {
        /**
         * The list of package identifiers this paywall will display.
         */
        public val packageIds: List<String>

        /**
         * The package to be selected by default.
         */
        public val defaultPackage: String?
            get() = null


        public  val imagesWebp: Images?
            get() = null


        public  val legacyImages: Images?
            get() = null

        /**
         * Whether the background image will be blurred (in templates with one).
         */
        public val blurredBackgroundImage: Boolean
            get() = false

        /**
         * Whether a restore purchases button should be displayed.
         */
        public val displayRestorePurchases: Boolean
            get() = true

        /**
         * If set, the paywall will display a terms of service link.
         */
        public val termsOfServiceURL: String?
            get() = null

        /**
         * If set, the paywall will display a privacy policy link.
         */
        public val privacyURL: String?
            get() = null

        /**
         * The set of colors used.
         */
        public val colors: ColorInformation

        /**
         * The images for this template.
         */
        public val images: Images
            get() {
                return Images(
                    header = imagesWebp?.header ?: legacyImages?.header,
                    background = imagesWebp?.background ?: legacyImages?.background,
                    icon = imagesWebp?.icon ?: legacyImages?.icon,
                )
            }

        public data class Images(
            /**
             * Image displayed as a header in a template.
             */
            
            val header: String? = null,

            /**
             * Image displayed as a background in a template.
             */
            
            val background: String? = null,

            /**
             * Image displayed as an app icon in a template.
             */
            
            val icon: String? = null,
        ) {
            internal val all: List<String>
                get() = listOfNotNull(header, background, icon)
        }

        
        public data class ColorInformation(
            /**
             * Set of colors for light mode.
             */
            val light: Colors,

            /**
             * Set of colors for dark mode.
             */
            val dark: Colors? = null,
        )

        
        public data class Colors(
            /**
             * Hex color for the background of the paywall.
             */
             val background: String,

            /**
             * Hex color for the primary text element.
             */
             val text1: String,

            /**
             * Hex color for secondary text element.
             */
             val text2: String? = null,

            /**
             * Hex color for tertiary text element.
             */
             val text3: String? = null,

            /**
             * Background hex color of the main call to action button.
             */
             val callToActionBackground: String,

            /**
             * Foreground hex color of the main call to action button.
             */
             val callToActionForeground: String,

            /**
             * If present, the CTA will create a vertical gradient from [callToActionBackground] to this hex color.
             */
            val callToActionSecondaryBackground: String? = null,

            /**
             * Primary accent hex color.
             */
             val accent1: String? = null,

            /**
             * Secondary accent hex color.
             */
             val accent2: String? = null,

            /**
             * Tertiary accent hex color.
             */
             val accent3: String? = null,
        )
    }

    /**
     * Defines the necessary localized information for a paywall.
     */
    public data class LocalizedConfiguration(
        /**
         * The title of the paywall screen.
         */
        val title: String,

        /**
         * The subtitle of the paywall screen.
         */
        
        val subtitle: String? = null,

        /**
         * The content of the main action button for purchasing a subscription.
         */
        
        val callToAction: String,

        /**
         * The content of the main action button for purchasing a subscription when an intro offer is available.
         * If `null`, no information regarding trial eligibility will be displayed.
         */
        
        
        val callToActionWithIntroOffer: String? = null,

        /**
         * The content of the main action button for purchasing a subscription when multiple intro offer are available.
         * This may happen in Google Play, if you have an offer with both a free trial and a discounted price.
         * If `null`, no information regarding trial eligibility will be displayed.
         */
        
        
        val callToActionWithMultipleIntroOffers: String? = null,

        /**
         * Description for the offer to be purchased.
         */
        
        
        val offerDetails: String? = null,

        /**
         * Description for the offer to be purchased when an intro offer is available.
         * If `null`, no information regarding trial eligibility will be displayed.
         */
        
        
        val offerDetailsWithIntroOffer: String? = null,

        /**
         * Description for the offer to be purchased when multiple intro offers are available.
         * This may happen in Google Play, if you have an offer with both a free trial and a discounted price.
         * If `null`, no information regarding trial eligibility will be displayed.
         */
        
        
        val offerDetailsWithMultipleIntroOffers: String? = null,

        /**
         * The name representing each of the packages, most commonly a variable.
         */
        
        
        val offerName: String? = null,

        /**
         * An optional list of features that describe this paywall.
         */
        val features: List<Feature> = emptyList(),
    ) {
        /**
         * An item to be showcased in a paywall.
         */
        
        public data class Feature(
            /**
             * The title of the feature.
             */
            val title: String,

            /**
             * An optional description of the feature.
             */
            val content: String? = null,

            /**
             * An optional icon for the feature.
             * This must be an icon identifier known by `RevenueCatUI`.
             */
             val iconID: String? = null,
        )
    }
}