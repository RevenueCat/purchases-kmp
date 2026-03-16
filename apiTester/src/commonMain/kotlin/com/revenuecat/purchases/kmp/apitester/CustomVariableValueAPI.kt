package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.ui.revenuecatui.CustomVariableValue

@Suppress("unused", "UNUSED_VARIABLE", "RedundantExplicitType")
private class CustomVariableValueAPI {
    fun check() {
        val stringVal: CustomVariableValue = CustomVariableValue.string("hello")
        val numberVal: CustomVariableValue = CustomVariableValue.number(3.14)
        val intNumberVal: CustomVariableValue = CustomVariableValue.number(5)
        val boolVal: CustomVariableValue = CustomVariableValue.boolean(true)

        val sv: CustomVariableValue.StringValue = CustomVariableValue.StringValue("hello")
        val nv: CustomVariableValue.NumberValue = CustomVariableValue.NumberValue(3.14)
        val nvInt: CustomVariableValue.NumberValue = CustomVariableValue.NumberValue(5)
        val nvLong: CustomVariableValue.NumberValue = CustomVariableValue.NumberValue(5L)
        val nvFloat: CustomVariableValue.NumberValue = CustomVariableValue.NumberValue(5.0f)
        val bv: CustomVariableValue.BooleanValue = CustomVariableValue.BooleanValue(true)

        val s: String = sv.value
        val n: Double = nv.value
        val b: Boolean = bv.value
    }
}
