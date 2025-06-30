package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import kotlinx.cinterop.CValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRect
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIViewController

/**
 * Will constrain the [constrainedViewController] to the given [constraints]. Make sure you
 * consistently update the [constraints], such as in a custom layout Modifier.
 */
internal class ConstrainingViewController<T: UIViewController>(
    val constrainedViewController: T,
    private val density: Density,
) : UIViewController(nibName = null, bundle = null) {

    var constraints: Constraints? = null

    override fun viewDidLoad() {
        super.viewDidLoad()
        view.addSubview(constrainedViewController.view)
    }

    override fun viewWillLayoutSubviews() {
        super.viewWillLayoutSubviews()
        val currentConstraints = constraints ?: return

        view.setFrame(view.frame.satisfy(currentConstraints))
    }

    private fun CValue<CGRect>.satisfy(constraints: Constraints): CValue<CGRect> = useContents {
        CGRectMake(
            x = origin.x,
            y = origin.y,
            width = constraints.fixedWidthPt ?: size.width,
            height = constraints.fixedHeightPt ?: size.height,
        )
    }

    private val Constraints.fixedWidthPt: Double?
        get() =
            if (hasBoundedWidth && hasFixedWidth) with(density) { maxWidth.toDp().value.toDouble() }
            else null

    private val Constraints.fixedHeightPt: Double?
        get() =
            if (hasBoundedHeight && hasFixedHeight) with(density) { maxHeight.toDp().value.toDouble() }
            else null
}
