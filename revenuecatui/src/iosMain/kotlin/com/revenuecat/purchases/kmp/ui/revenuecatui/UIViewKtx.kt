package com.revenuecat.purchases.kmp.ui.revenuecatui

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import platform.UIKit.UIView

private fun UIView.getIntrinsicContentHeight(): Int {
    var size: Int? = null
    memScoped { size = intrinsicContentSize.ptr.pointed.height.toInt() }
    return size!!
}

private fun UIView.getIntrinsicContentWidth(): Int {
    var size: Int? = null
    memScoped { size = intrinsicContentSize.ptr.pointed.width.toInt() }
    return size!!
}

internal fun UIView.getIntrinsicContentHeightOfFirstSubView(): Int? =
    (subviews.firstOrNull() as? UIView)?.getIntrinsicContentHeight()

internal fun UIView.getIntrinsicContentWidthOfFirstSubView(): Int? =
    (subviews.firstOrNull() as? UIView)?.getIntrinsicContentWidth()
