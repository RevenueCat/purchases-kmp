package com.revenuecat.purchases.kmp.ui.revenuecatui.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import com.revenuecat.purchases.kmp.ui.revenuecatui.ViewControllerWrapper
import platform.UIKit.UIViewController

/**
 * Improves layout behavior for UIKit ViewControllers when:
 * - the content size is not fixed, or
 * - the content size is animated.
 */
internal fun <T: UIViewController> Modifier.layoutViewController(
    viewControllerWrapper: ViewControllerWrapper<T>,
    intrinsicContentSizePx: () -> Int,
): Modifier = layout { measurable, constraints ->
    // Check if we are being asked to wrap our own content height. If so, we will use the
    // measurement done by UIKit.
    val constraintsToUse = if (!constraints.hasFixedHeight)
        intrinsicContentSizePx()
            .coerceAtMost(constraints.maxHeight)
            .let { height -> constraints.copy(minHeight = height, maxHeight = height) }
    else constraints

    viewControllerWrapper.applyConstraints(constraintsToUse, this)
    val placeable = measurable.measure(constraintsToUse)

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}
