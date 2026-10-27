## RevenueCat SDK
### 💥 Breaking Changes
* Updates purchases-android to 11.0.0, which uses Google Play Billing Library 9.1.0 and Jetpack Compose BOM 2026.06.01. Android apps must compile against API 35 or later. When the Play Store is blocked on the device, purchases now fail with `PurchaseNotAllowedError` instead of `StoreProblemError`.
* Updates Arrow in `purchases-kmp-either` to 2.2.2.1. Apps using this artifact must compile against API 36 or later, and code relying on APIs deprecated in Arrow 1.x needs to migrate.

The minimum Android version (API 23) and Kotlin version (2.3.20) are unchanged. For more information, please check our [migration guide](migrations/4.0.0-MIGRATION.md)

* Update Arrow to 2.2.2.1 (#1031) via Álvaro Brey (@AlvaroBrey)

### ✨ New Features
* `purchases-kmp-either` now publishes a `watchosDeviceArm64` target.

### 🔄 Other Changes
* Build against purchases-android 11.0.0-SNAPSHOT (#1088) via Álvaro Brey (@AlvaroBrey)
* Add the 4.0.0 migration guide (#1080) via Álvaro Brey (@AlvaroBrey)
