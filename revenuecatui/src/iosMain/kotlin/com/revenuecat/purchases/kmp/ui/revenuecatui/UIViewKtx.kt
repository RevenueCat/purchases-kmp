package com.revenuecat.purchases.kmp.ui.revenuecatui

import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import platform.UIKit.UIView

private fun UIView.getIntrinsicContentSize(): Int {
    var size: Int? = null
    memScoped { size = intrinsicContentSize.ptr.pointed.height.toInt() }
    return size!!
}
public fun UIView.getIntrinsicContentSizeOfFirstSubView(): Int? =
    (subviews.firstOrNull() as? UIView)?.getIntrinsicContentSize()