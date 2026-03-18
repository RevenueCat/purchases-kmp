package com.revenuecat.purchases.kmp.ui.revenuecatui

/**
 * Represents a value for a custom paywall variable. Custom variables allow you to
 * dynamically set text on your paywall from your app.
 */
public sealed class CustomVariableValue {

    /**
     * A string custom variable value.
     */
    public class StringValue(public val value: String) : CustomVariableValue() {
        override fun equals(other: Any?): kotlin.Boolean =
            other is StringValue && value == other.value

        override fun hashCode(): Int = value.hashCode()
        override fun toString(): String = "CustomVariableValue.StringValue(value=$value)"
    }

    /**
     * A numeric custom variable value.
     */
    public class NumberValue(public val value: Double) : CustomVariableValue() {
        public constructor(value: Int) : this(value.toDouble())
        public constructor(value: Long) : this(value.toDouble())
        public constructor(value: Float) : this(value.toDouble())

        override fun equals(other: Any?): kotlin.Boolean =
            other is NumberValue && value == other.value

        override fun hashCode(): Int = value.hashCode()
        override fun toString(): String = "CustomVariableValue.NumberValue(value=$value)"
    }

    /**
     * A boolean custom variable value.
     */
    public class BooleanValue(public val value: Boolean) : CustomVariableValue() {
        override fun equals(other: Any?): kotlin.Boolean =
            other is BooleanValue && value == other.value

        override fun hashCode(): Int = value.hashCode()
        override fun toString(): String = "CustomVariableValue.BooleanValue(value=$value)"
    }

    public companion object {
        /**
         * Creates a string custom variable value.
         */
        public fun string(value: String): CustomVariableValue = StringValue(value)

        /**
         * Creates a numeric custom variable value.
         */
        public fun number(value: Double): CustomVariableValue = NumberValue(value)

        /**
         * Creates a numeric custom variable value from an integer.
         */
        public fun number(value: Int): CustomVariableValue = NumberValue(value)

        /**
         * Creates a boolean custom variable value.
         */
        public fun boolean(value: Boolean): CustomVariableValue = BooleanValue(value)
    }
}
