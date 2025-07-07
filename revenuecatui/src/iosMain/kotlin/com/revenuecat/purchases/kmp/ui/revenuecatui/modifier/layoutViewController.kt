package com.revenuecat.purchases.kmp.ui.revenuecatui.modifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.revenuecat.purchases.kmp.ui.revenuecatui.getIntrinsicContentSizeOfFirstSubView
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIViewController

/**
 * Improves layout behavior for UIKit ViewControllers when:
 * - the content size is not fixed, or
 * - the content size is animated.
 *
 * @param state Use [rememberLayoutViewControllerState] to get a state instance.
 */
internal fun Modifier.layoutViewController(
    state: LayoutViewControllerState
): Modifier = this then LayoutViewControllerElement(
    viewControllerProvider = { state.viewController },
    intrinsicContentHeightProvider = { state.intrinsicContentHeight },
)

/**
 * @param getIntrinsicContentHeight A function that returns the intrinsic content height of the
 * ViewController. Defaults to the intrinsic content height of the first subview.
 */
@Composable
internal fun rememberLayoutViewControllerState(
    getIntrinsicContentHeight: UIViewController.() -> Dp = {
        view.getIntrinsicContentSizeOfFirstSubView()?.dp ?: 0.dp
    },
): LayoutViewControllerState = remember {
    LayoutViewControllerState(
        getIntrinsicContentHeight = getIntrinsicContentHeight,
    )
}

@Stable
internal class LayoutViewControllerState(
    private val getIntrinsicContentHeight: UIViewController.() -> Dp,
    ) {
    internal var viewController: UIViewController? = null
        private set
    internal var intrinsicContentHeight: Dp by mutableStateOf(0.dp)
        private set

    fun setViewController(viewController: UIViewController) {
        this.viewController = viewController
        updateIntrinsicContentHeight()
    }

    fun updateIntrinsicContentHeight() {
        val intrinsicContentHeight = viewController?.getIntrinsicContentHeight()
        if (intrinsicContentHeight != null) this.intrinsicContentHeight = intrinsicContentHeight
    }
}

private data class LayoutViewControllerElement(
    val viewControllerProvider: () -> UIViewController?,
    val intrinsicContentHeightProvider: () -> Dp,
) : ModifierNodeElement<LayoutViewController>() {

    override fun create() = LayoutViewController(
        viewControllerProvider = viewControllerProvider,
        intrinsicContentHeightProvider = intrinsicContentHeightProvider,
    )

    override fun update(node: LayoutViewController) {
        // Not needed. Everything is read lazily.
    }
}

private class LayoutViewController(
    val viewControllerProvider: () -> UIViewController?,
    val intrinsicContentHeightProvider: () -> Dp,
) : LayoutModifierNode, Modifier.Node() {

    var activeConstraints: List<NSLayoutConstraint> = emptyList()

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        // Check if we are being asked to wrap our own content height. If so, we will use the
        // measurement done by UIKit.
        val constraintsToUse = if (!constraints.hasFixedHeight)
            intrinsicContentHeightProvider()
                .roundToPx()
                .coerceAtMost(constraints.maxHeight)
                .let { height -> constraints.copy(minHeight = height, maxHeight = height) }
        else constraints

        val viewController = viewControllerProvider()
        if (viewController?.isViewLoaded() == true) {
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
