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


@Composable
internal fun rememberLayoutViewControllerState(
    getIntrinsicContentHeight: UIViewController.() -> Dp = {
        println("TESTING getIntrinsicContentHeight, ${view.subviews.size} subviews")
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
        println("TESTING setViewController, ${viewController.view.subviews.size} subviews")
        this.viewController = viewController
        updateIntrinsicContentHeight()
    }

    fun updateIntrinsicContentHeight() {
        val intrinsicContentHeight = viewController?.getIntrinsicContentHeight()
        println("TESTING updateIntrinsicContentHeight, intrinsicContentHeight: $intrinsicContentHeight")

        if (intrinsicContentHeight != null) {
            this.intrinsicContentHeight = intrinsicContentHeight
            return
        }
    }
}


/**
 * Improves layout behavior for UIKit ViewControllers when:
 * - the content size is not fixed, or
 * - the content size is animated.
 */
@Composable
internal fun Modifier.layoutViewController(
    state: LayoutViewControllerState
): Modifier = this then LayoutViewControllerElement(
    viewControllerProvider = { state.viewController },
    intrinsicContentHeight = state.intrinsicContentHeight,
    intrinsicContentHeightProvider = { state.intrinsicContentHeight },
)

private class LayoutViewController(
    var viewControllerProvider: () -> UIViewController?,
    var intrinsicContentHeight: Dp,
    val intrinsicContentHeightProvider: () -> Dp,
) : LayoutModifierNode, Modifier.Node() {

    var activeConstraints: List<NSLayoutConstraint> = emptyList()

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        println("TESTING measure, intrinsicContentHeight: ${intrinsicContentHeight}")
        // Check if we are being asked to wrap our own content height. If so, we will use the
        // measurement done by UIKit.
        val constraintsToUse = if (!constraints.hasFixedHeight)
            intrinsicContentHeightProvider()
                .roundToPx()
                .coerceAtMost(constraints.maxHeight)
                .let { height -> constraints.copy(minHeight = height, maxHeight = height) }
        else constraints

        val viewController = viewControllerProvider()
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

private data class LayoutViewControllerElement(
    val viewControllerProvider: () -> UIViewController?,
    val intrinsicContentHeight: Dp,
) : ModifierNodeElement<LayoutViewController>() {

    private var intrinsicContentHeightProvider: () -> Dp = { intrinsicContentHeight }

    constructor(
        viewControllerProvider: () -> UIViewController?,
        intrinsicContentHeight: Dp,
        intrinsicContentHeightProvider: () -> Dp
    ): this(
        viewControllerProvider = viewControllerProvider,
        intrinsicContentHeight = intrinsicContentHeight,
    ) {
        this.intrinsicContentHeightProvider = intrinsicContentHeightProvider
    }

    override fun create() = LayoutViewController(
        viewControllerProvider = viewControllerProvider,
        intrinsicContentHeight = intrinsicContentHeight,
        intrinsicContentHeightProvider = intrinsicContentHeightProvider,
    )

    override fun update(node: LayoutViewController) {
        println("TESTING update intrinsicContentHeight, from${node.intrinsicContentHeight} to ${intrinsicContentHeight}")
        node.viewControllerProvider = viewControllerProvider
        node.intrinsicContentHeight = intrinsicContentHeight
    }
}
