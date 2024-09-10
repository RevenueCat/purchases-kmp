package com.revenuecat.purchases.kmp.sample

internal sealed interface AsyncState<out T> {
    data object Loading : AsyncState<Nothing>
    data class Loaded<T>(val value: T) : AsyncState<T>
    data object Error : AsyncState<Nothing>
}
