package com.revenuecat.purchases.kmp.apitester

import com.revenuecat.purchases.kmp.ui.revenuecatui.CustomVariableValue

@Suppress("unused", "UNUSED_VARIABLE", "RedundantExplicitType")
private class CustomVariableValueAPI {
    fun check() {
        val stringVal: CustomVariableValue = CustomVariableValue.string("hello")
        val numberVal: CustomVariableValue = CustomVariableValue.number(3.14)
        val intNumberVal: CustomVariableValue = CustomVariableValue.number(5)
        val boolVal: CustomVariableValue = CustomVariableValue.boolean(true)

        val sv: CustomVariableValue.String = CustomVariableValue.String("hello")
        val nv: CustomVariableValue.Number = CustomVariableValue.Number(3.14)
        val nvInt: CustomVariableValue.Number = CustomVariableValue.Number(5)
        val nvLong: CustomVariableValue.Number = CustomVariableValue.Number(5L)
        val nvFloat: CustomVariableValue.Number = CustomVariableValue.Number(5.0f)
        val bv: CustomVariableValue.Boolean = CustomVariableValue.Boolean(true)

        val s: kotlin.String = sv.value
        val n: Double = nv.value
        val b: kotlin.Boolean = bv.value
    }
}
