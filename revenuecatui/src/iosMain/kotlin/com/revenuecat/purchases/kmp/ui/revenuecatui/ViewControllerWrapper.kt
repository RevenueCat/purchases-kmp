package com.revenuecat.purchases.kmp.ui.revenuecatui

import androidx.compose.runtime.remember
import platform.UIKit.UIViewController

/**
 * Can be [remembered][remember] before the [wrapped] ViewController is instantiated, so as to
 * "reserve" a spot in the Compose slot table.
 */
internal class ViewControllerWrapper<T: UIViewController>(var wrapped: T?)
