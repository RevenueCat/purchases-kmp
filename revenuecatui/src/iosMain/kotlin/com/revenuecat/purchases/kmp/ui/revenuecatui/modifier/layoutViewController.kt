package com.revenuecat.purchases.kmp.ui.revenuecatui.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import com.revenuecat.purchases.kmp.ui.revenuecatui.ViewControllerWrapper
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIViewController

/**
 * Improves layout behavior for UIKit ViewControllers when:
 * - the content size is not fixed, or
 * - the content size is animated.
 */
internal fun <T: UIViewController> Modifier.layoutViewController(
    viewControllerWrapper: ViewControllerWrapper<T>,
    intrinsicContentSizePx: () -> Int,
) = this then LayoutViewControllerElement(viewControllerWrapper, intrinsicContentSizePx)

private class LayoutViewController<T: UIViewController>(
    var viewControllerWrapper: ViewControllerWrapper<T>,
    var intrinsicContentSizePx: () -> Int,
) : LayoutModifierNode, Modifier.Node() {

    var activeConstraints: List<NSLayoutConstraint> = emptyList()

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        // Check if we are being asked to wrap our own content height. If so, we will use the
        // measurement done by UIKit.
        val constraintsToUse = if (!constraints.hasFixedHeight)
            intrinsicContentSizePx()
                .coerceAtMost(constraints.maxHeight)
                .let { height -> constraints.copy(minHeight = height, maxHeight = height) }
        else constraints

        val viewController = viewControllerWrapper.wrapped
        if (viewController != null && viewController.isViewLoaded()) {
            // Deactivate and clear existing constraints
            NSLayoutConstraint.deactivateConstraints(activeConstraints)
            activeConstraints = viewController.applyConstraints(constraintsToUse, this)
        }

        val placeable = measurable.measure(constraintsToUse)

        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }

    private fun UIViewController.applyConstraints(
        constraints: Constraints,
        density: Density,
    ): List<NSLayoutConstraint> {
        view.translatesAutoresizingMaskIntoConstraints = false

        val newConstraints = buildList {
            constraints
                .fixedWidthPt(density)
                ?.also { width -> add(view.widthAnchor.constraintEqualToConstant(width)) }

            constraints
                .fixedHeightPt(density)
                ?.also { height -> add(view.heightAnchor.constraintEqualToConstant(height)) }
        }

        NSLayoutConstraint.activateConstraints(newConstraints)
        return newConstraints
    }

    private fun Constraints.fixedWidthPt(density: Density): Double? =
        if (hasBoundedWidth && hasFixedWidth) with(density) { maxWidth.toDp().value.toDouble() }
        else null

    private fun Constraints.fixedHeightPt(density: Density): Double? =
        if (hasBoundedHeight && hasFixedHeight) with(density) { maxHeight.toDp().value.toDouble() }
        else null
}

private data class LayoutViewControllerElement<T: UIViewController>(
    val viewControllerWrapper: ViewControllerWrapper<T>,
    val intrinsicContentSizePx: () -> Int,
) : ModifierNodeElement<LayoutViewController<T>>() {
    override fun create() = LayoutViewController(
        viewControllerWrapper = viewControllerWrapper,
        intrinsicContentSizePx = intrinsicContentSizePx,
    )

    override fun update(node: LayoutViewController<T>) {
        node.viewControllerWrapper = viewControllerWrapper
        node.intrinsicContentSizePx = intrinsicContentSizePx
    }
}
