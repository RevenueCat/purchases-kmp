package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIViewController

/**
 * Can be [remembered][remember] before the [wrapped] ViewController is instantiated, so as to
 * "reserve" a spot in the Compose slot table.
 */
internal class ViewControllerWrapper<T: UIViewController>(var wrapped: T?) {

    private var activeConstraints: List<NSLayoutConstraint> = emptyList()

    fun applyConstraints(constraints: Constraints, density: Density) {
        // Only proceed if the view is loaded
        val viewController = wrapped
        if (viewController == null || !viewController.isViewLoaded()) return

        // Deactivate and clear existing constraints
        NSLayoutConstraint.deactivateConstraints(activeConstraints)
        activeConstraints = emptyList()

        viewController.view.translatesAutoresizingMaskIntoConstraints = false

        val newConstraints = mutableListOf<NSLayoutConstraint>()

        // Add width constraint if we have a fixed width
        constraints.fixedWidthPt(density)?.let { width ->
            newConstraints.add(
                viewController.view.widthAnchor.constraintEqualToConstant(width)
            )
            println("width constraint: $width")
        }

        // Add height constraint if we have a fixed height
        constraints.fixedHeightPt(density)?.let { height ->
            newConstraints.add(
                viewController.view.heightAnchor.constraintEqualToConstant(height)
            )
            println("height constraint: $height")
        }

        // Activate the new constraints
        NSLayoutConstraint.activateConstraints(newConstraints)
        activeConstraints = newConstraints
    }

    private fun Constraints.fixedWidthPt(density: Density): Double? =
            if (hasBoundedWidth && hasFixedWidth) with(density) { maxWidth.toDp().value.toDouble() }
            else null

    private fun Constraints.fixedHeightPt(density: Density): Double? =
            if (hasBoundedHeight && hasFixedHeight) with(density) { maxHeight.toDp().value.toDouble() }
            else null
}