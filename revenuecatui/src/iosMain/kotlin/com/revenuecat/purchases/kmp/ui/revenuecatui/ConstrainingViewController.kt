package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIViewController

/**
 * Will constrain the [constrainedViewController] to the given [constraints]. Make sure you
 * consistently update the [constraints], such as in a custom layout Modifier.
 */
internal class ConstrainingViewController<T: UIViewController>(
    val constrainedViewController: T,
    private val density: Density,
) : UIViewController(nibName = null, bundle = null) {

    private var activeConstraints: List<NSLayoutConstraint> = emptyList()
    private var _constraints: Constraints? = null
    
    var constraints: Constraints?
        get() = _constraints
        set(value) {
            _constraints = value
            updateNSLayoutConstraints()
        }

    override fun viewDidLoad() {
        super.viewDidLoad()
        view.addSubview(constrainedViewController.view)
        
        // Set up the constrained view for Auto Layout
        constrainedViewController.view.translatesAutoresizingMaskIntoConstraints = false
        
        // Update constraints in case they were set before viewDidLoad
        updateNSLayoutConstraints()
    }

    private fun updateNSLayoutConstraints() {
        // Only proceed if the view is loaded
        if (!isViewLoaded()) return
        
        // Deactivate and clear existing constraints
        NSLayoutConstraint.deactivateConstraints(activeConstraints)
        activeConstraints = emptyList()
        
        val currentConstraints = _constraints ?: return
        val constrainedView = constrainedViewController.view
        
        val newConstraints = mutableListOf<NSLayoutConstraint>()
        
        // Add width constraint if we have a fixed width
        currentConstraints.fixedWidthPt?.let { width ->
            newConstraints.add(
                constrainedView.widthAnchor.constraintEqualToConstant(width)
            )
        }
        
        // Add height constraint if we have a fixed height
        currentConstraints.fixedHeightPt?.let { height ->
            newConstraints.add(
                constrainedView.heightAnchor.constraintEqualToConstant(height)
            )
        }
        
        // Center the constrained view in the container
        newConstraints.addAll(listOf(
            constrainedView.centerXAnchor.constraintEqualToAnchor(view.centerXAnchor),
            constrainedView.centerYAnchor.constraintEqualToAnchor(view.centerYAnchor)
        ))
        
        // Activate the new constraints
        NSLayoutConstraint.activateConstraints(newConstraints)
        activeConstraints = newConstraints
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
