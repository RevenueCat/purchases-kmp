package io.shortway.kobankat.ui.revenuecatui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import cocoapods.PurchasesHybridCommonUI.RCPaywallFooterViewController
import objcnames.classes.RCOffering

/**
 * A Paywall footer.
 */
@Composable
public actual fun PaywallFooter(
    options: PaywallOptions,
    mainContent: @Composable ((PaddingValues) -> Unit)?,
) {
    val paywallComposable = @Composable {
        UIKitView(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            factory = {
                RCPaywallFooterViewController(
                    offering = options.offering as RCOffering,
                    displayCloseButton = false,
                    shouldBlockTouchEvents = false,
                    dismissRequestedHandler = { options.dismissRequest() }
                ).apply { setDelegate(PaywallViewControllerDelegate(options.onRestoreCompleted)) }
                    .view
            }
        )
    }

    // Largely the same as PaywallFooter in purchases:ui on Android.
    if (mainContent == null) {
        paywallComposable()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(-DefaultCornerRadius),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                mainContent(PaddingValues(bottom = DefaultCornerRadius))
            }
            paywallComposable()
        }
    }

}

private val DefaultCornerRadius = 20.dp
