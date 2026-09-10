package com.revenuecat.purchases.kmp.ui.revenuecatui

import kotlin.reflect.KClass
import kotlin.reflect.safeCast

/**
 * A paywall control interaction, as passed to [PaywallListener.onInteraction].
 */
public class PaywallInteractionEvent internal constructor(
    /** The interaction as snake_case keys ([Key.name]) for analytics SDKs; keys that do not apply are absent. */
    public val rawProperties: Map<String, Any>,
) {

    /** The value for [key], or null when it does not apply to this interaction. */
    public fun <T : Any> getProperty(key: Key<T>): T? {
        val value = rawProperties[key.name] ?: return null
        val converted: Any? = when (key.type) {
            Int::class -> (value as? Number)?.toInt()
            Long::class -> (value as? Number)?.toLong()
            else -> value
        }
        return key.type.safeCast(converted)
    }

    override fun equals(other: Any?): Boolean =
        this === other || (other is PaywallInteractionEvent && other.rawProperties == rawProperties)

    override fun hashCode(): Int = rawProperties.hashCode()

    override fun toString(): String = "PaywallInteractionEvent($rawProperties)"

    /** A typed property of a [PaywallInteractionEvent]; see [Keys]. */
    public class Key<T : Any> internal constructor(
        public val name: String,
        internal val type: KClass<T>,
    )

    /** The properties an interaction can carry, for use with [getProperty]. */
    public object Keys {
        /** Milliseconds since the epoch. */
        public val TIMESTAMP: Key<Long> = Key("timestamp", Long::class)
        public val SESSION_ID: Key<String> = Key("session_id", String::class)
        public val OFFERING_ID: Key<String> = Key("offering_id", String::class)
        public val PAYWALL_ID: Key<String> = Key("paywall_id", String::class)
        public val PAYWALL_REVISION: Key<Int> = Key("paywall_revision", Int::class)
        public val DISPLAY_MODE: Key<String> = Key("display_mode", String::class)
        public val DARK_MODE: Key<Boolean> = Key("dark_mode", Boolean::class)
        public val LOCALE: Key<String> = Key("locale", String::class)
        public val COMPONENT_TYPE: Key<String> = Key("component_type", String::class)
        public val COMPONENT_VALUE: Key<String> = Key("component_value", String::class)
        public val COMPONENT_NAME: Key<String> = Key("component_name", String::class)
        public val COMPONENT_URL: Key<String> = Key("component_url", String::class)
        public val ORIGIN_INDEX: Key<Int> = Key("origin_index", Int::class)
        public val DESTINATION_INDEX: Key<Int> = Key("destination_index", Int::class)
        public val ORIGIN_CONTEXT_NAME: Key<String> = Key("origin_context_name", String::class)
        public val DESTINATION_CONTEXT_NAME: Key<String> = Key("destination_context_name", String::class)
        public val DEFAULT_INDEX: Key<Int> = Key("default_index", Int::class)
        public val ORIGIN_PACKAGE_ID: Key<String> = Key("origin_package_id", String::class)
        public val DESTINATION_PACKAGE_ID: Key<String> = Key("destination_package_id", String::class)
        public val DEFAULT_PACKAGE_ID: Key<String> = Key("default_package_id", String::class)
        public val CURRENT_PACKAGE_ID: Key<String> = Key("current_package_id", String::class)
        public val RESULTING_PACKAGE_ID: Key<String> = Key("resulting_package_id", String::class)
        public val ORIGIN_PRODUCT_ID: Key<String> = Key("origin_product_id", String::class)
        public val DESTINATION_PRODUCT_ID: Key<String> = Key("destination_product_id", String::class)
        public val DEFAULT_PRODUCT_ID: Key<String> = Key("default_product_id", String::class)
        public val CURRENT_PRODUCT_ID: Key<String> = Key("current_product_id", String::class)
        public val RESULTING_PRODUCT_ID: Key<String> = Key("resulting_product_id", String::class)
    }

    /**
     * Known values of [Keys.COMPONENT_TYPE].
     */
    public object ComponentTypes {
        public const val TAB: String = "tab"
        public const val SWITCH: String = "switch"
        public const val CAROUSEL: String = "carousel"
        public const val BUTTON: String = "button"
        public const val TEXT: String = "text"
        public const val PACKAGE: String = "package"
        public const val PACKAGE_SELECTION_SHEET: String = "package_selection_sheet"
        public const val PURCHASE_BUTTON: String = "purchase_button"
    }
}
