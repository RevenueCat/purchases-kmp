package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.revenuecat.purchases.ui.revenuecatui.customercenter.CustomerCenter as RcCustomerCenter

@Composable
public actual fun CustomerCenter(
    modifier: Modifier,
    onDismiss: () -> Unit
): Unit =
    RcCustomerCenter(
        modifier = modifier.fillMaxSize(),
        onDismiss = onDismiss
    )
