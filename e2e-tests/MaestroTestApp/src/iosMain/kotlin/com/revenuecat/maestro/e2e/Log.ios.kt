package com.revenuecat.maestro.e2e

import platform.Foundation.NSLog

actual fun logDebug(message: String) {
    NSLog("MaestroTestApp: %@", message)
}
