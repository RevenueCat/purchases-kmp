package com.revenuecat.purchases.kmp.sample

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

private val _deepLinkFlow = MutableSharedFlow<String>(
    replay = 1,
)

val deepLinkFlow: Flow<String> = _deepLinkFlow.asSharedFlow()

@Suppress("unused")
fun handleDeepLink(url: String) {
    // This should be a suspend function and use `emit` instead.
    // But using this here for simplicity since it's a test app.
    _deepLinkFlow.tryEmit(url)
}
