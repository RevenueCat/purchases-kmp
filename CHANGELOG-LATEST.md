## 1.0.0-beta.3 API Changes

This latest release updates the Android SDK dependency from v7 to [v8](https://github.com/RevenueCat/purchases-android/releases/tag/6.0.0) to use BillingClient 7 and updates the iOS SDK dependency from v4 to v5 to use StoreKit 2 by default in the SDK.

### Migration Guides

- For a detailed guide to all of the changes, please see the [KMP V1.0.0-beta.3 Migration Guide](https://github.com/RevenueCat/purchases-kmp/blob/main/migrations/1.0.0-beta.3-MIGRATION.md).
- See [Android Native - V8 API Migration Guide](https://github.com/RevenueCat/purchases-android/blob/main/migrations/v8-MIGRATION.md) for a more thorough explanation of the Android changes.
- See [iOS Native - V5 Migration Guide](https://github.com/RevenueCat/purchases-ios/blob/main/Sources/DocCDocumentation/DocCDocumentation.docc/V5_API_Migration_guide.md) for a more thorough explanation of the iOS changes. Notably, this version uses StoreKit 2 to process purchases by default.

### New Minimum OS Versions

This release raises the minimum required OS versions to the following:

- iOS 13.0
- tvOS 13.0
- watchOS 6.2
- macOS 10.15
- Android: SDK 21 (Android 5.0)

### In-App Purchase Key Required for StoreKit 2

In order to use StoreKit 2, you must configure your In-App Purchase Key in the RevenueCat dashboard. You can find instructions describing how to do this [here](https://www.revenuecat.com/docs/in-app-purchase-key-configuration).

### Breaking Changes

- Adopt Latest PHC Version (#139) via Will Taylor (@fire-at-will)

### Other Changes

- Accept Fastlane Changes (#151) via Will Taylor (@fire-at-will)
- Adds the git-town config to source control. (#146) via JayShortway (@JayShortway)
- Appends PHC version to the library version as build metadata (#136) via JayShortway (@JayShortway)
- Adds required Kotlin test dependencies to :core (#142) via JayShortway (@JayShortway)
- Temporarily reverts "Remove expect/actual from enums without extra properties (#137)" (#141) via JayShortway (@JayShortway)
- Remove expect/actual from enums that lack extra properties (#137) via JayShortway (@JayShortway)
