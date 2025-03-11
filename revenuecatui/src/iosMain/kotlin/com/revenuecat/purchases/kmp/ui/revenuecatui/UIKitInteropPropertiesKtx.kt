package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.viewinterop.UIKitInteropProperties

/**
 * Uses NonCooperative UIKitInteropInteractionMode if available, for snappy scrolling.
 */
@OptIn(ExperimentalComposeUiApi::class)
internal fun nonCooperativeUiKitInteropPropertiesNonExperimental(): UIKitInteropProperties =
    try {
        // This UIKitInteropProperties constructor and the UIKitInteropInteractionMode type are
        // experimental at the time of writing. We try to use them, but this could fail if we're
        // linked against a newer version of CMP that changed this API. If so, we fall back to the
        // non-experimental constructor.
        // In that scenario, there's an added delay when scrolling the paywall.
        UIKitInteropProperties(
            // Not importing this type on purpose, because it is experimental, and we want to catch
            // any linkage errors here.
            interactionMode = androidx.compose.ui.viewinterop.UIKitInteropInteractionMode.NonCooperative,
        )
    } catch (e: Error) {
        // It would actually throw an IrLinkageError, but that type is internal, so we're catching
        // its supertype instead (which is basically a Throwable).
        UIKitInteropProperties(
            isInteractive = true,
            isNativeAccessibilityEnabled = false,
        )
    }
