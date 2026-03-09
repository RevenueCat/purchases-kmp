package com.revenuecat.purchases.kmp.sample

import kotlinx.cinterop.cValue
import platform.Foundation.NSOperatingSystemVersion
import platform.Foundation.NSProcessInfo

actual val isCodeRedemptionSheetAvailable: Boolean =
    NSProcessInfo.processInfo.isOperatingSystemAtLeastVersion(
        cValue<NSOperatingSystemVersion> {
            majorVersion = 14
            minorVersion = 0
            patchVersion = 0
        }
    )
