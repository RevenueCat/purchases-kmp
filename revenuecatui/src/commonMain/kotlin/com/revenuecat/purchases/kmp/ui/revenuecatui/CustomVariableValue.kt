package com.revenuecat.purchases.kmp.ui.revenuecatui

/**
 * Represents a value for a custom paywall variable. Custom variables allow you to
 * dynamically set text on your paywall from your app.
 */
public abstract class CustomVariableValue internal constructor() {

    /**
     * A string custom variable value.
     */
    public class String(public val value: kotlin.String) : CustomVariableValue() {
        override fun equals(other: Any?): kotlin.Boolean =
            other is String && value == other.value

        override fun hashCode(): Int = value.hashCode()
        override fun toString(): kotlin.String = "CustomVariableValue.String(value=$value)"
    }

    /**
     * A numeric custom variable value.
     */
    public class Number(public val value: kotlin.Double) : CustomVariableValue() {
        public constructor(value: Int) : this(value.toDouble())
        public constructor(value: Long) : this(value.toDouble())
        public constructor(value: Float) : this(value.toDouble())

        override fun equals(other: Any?): kotlin.Boolean =
            other is Number && value == other.value

        override fun hashCode(): Int = value.hashCode()
        override fun toString(): kotlin.String = "CustomVariableValue.Number(value=$value)"
    }

    /**
     * A boolean custom variable value.
     */
    public class Boolean(public val value: kotlin.Boolean) : CustomVariableValue() {
        override fun equals(other: Any?): kotlin.Boolean =
            other is Boolean && value == other.value

        override fun hashCode(): Int = value.hashCode()
        override fun toString(): kotlin.String = "CustomVariableValue.Boolean(value=$value)"
    }

    public companion object {
        /**
         * Creates a string custom variable value.
         */
        public fun string(value: kotlin.String): CustomVariableValue = String(value)

        /**
         * Creates a numeric custom variable value.
         */
        public fun number(value: kotlin.Double): CustomVariableValue = Number(value)

        /**
         * Creates a numeric custom variable value from an integer.
         */
        public fun number(value: Int): CustomVariableValue = Number(value)

        /**
         * Creates a boolean custom variable value.
         */
        public fun boolean(value: kotlin.Boolean): CustomVariableValue = Boolean(value)
    }
}
