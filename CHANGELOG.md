## 1.0.0-beta.3
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

## 1.0.0-beta.2
### Breaking Changes
* Removes Offerings.getCurrentOfferingForPlacement() (#121) via JayShortway (@JayShortway)
### Bugfixes
* Fix iOS crash showing paywall for current offering (#129) via JayShortway (@JayShortway)
### Dependency Updates
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 11.1.1 (#124) via RevenueCat Git Bot (@RCGitBot)
  * [Android 7.12.0](https://github.com/RevenueCat/purchases-android/releases/tag/7.12.0)
  * [iOS 4.43.2](https://github.com/RevenueCat/purchases-ios/releases/tag/4.43.2)
  * [iOS 4.43.1](https://github.com/RevenueCat/purchases-ios/releases/tag/4.43.1)
### Other Changes
* Improves the error message when the PurchasesInitializer is missing from the merged AndroidManifest.xml. (#130) via JayShortway (@JayShortway)
* Fixes viewing revenuecatui Kotlin code in Xcode (#127) via JayShortway (@JayShortway)
* Removes the Compose Compiler Gradle Plugin from :apiTester. (#125) via JayShortway (@JayShortway)
* Adds missing API tests (#106) via JayShortway (@JayShortway)
* Publishes API reference only on release. (#122) via JayShortway (@JayShortway)
* Fixes the release pipeline (#119) via JayShortway (@JayShortway)
* Adds KobanKat migration guide (#116) via JayShortway (@JayShortway)
* Updates README.md (#115) via JayShortway (@JayShortway)

## 1.0.0-beta.1
### Breaking Changes

* Changes package to com.revenuecat (#80) via JayShortway (@JayShortway)
* Fix iOS StoreProduct description & price (#108) via JayShortway (@JayShortway)
* Uses PurchasesAreCompletedBy (#105) via JayShortway (@JayShortway)
* PurchasesConfiguration binary compatibility (#72) via JayShortway (@JayShortway)
* Removes PurchasesFactory (#103) via JayShortway (@JayShortway)
* configuration consistency (#71) via JayShortway (@JayShortway)
* Removes StoreProduct.pricePer*() functions, as they are not available in PHC, nor in e.g.
  Flutter & React Native. via JayShortway (@JayShortway)
### New Features
* Adds ProductCategory to StoreProduct (#114) via JayShortway (@JayShortway)
* Checks PHC version at configure() time on iOS (#104) via JayShortway (@JayShortway)
### Bugfixes
* Fixes Compose<->K2 compilation error (#111) via JayShortway (@JayShortway)
* Fixes the Transaction type (#113) via JayShortway (@JayShortway)
* Fixes showInAppMessagesIfNeeded() on iOS (#110) via JayShortway (@JayShortway)
### Dependency Updates
* Consolidates Compose versions (#112) via JayShortway (@JayShortway)
* Update dependency org.jetbrains.kotlinx:kotlinx-datetime to v0.6.0 (#66) via JayShortway (
  @JayShortway)
* Update agp to v8.4.1 (#68) via JayShortway (@JayShortway)
* Update agp to v8.4.0 (#62) via JayShortway (@JayShortway)
### Other Changes
* Adds .aiexclude (#109) via JayShortway (@JayShortway)
* Fixes snapshot publishing (#107) via JayShortway (@JayShortway)
* Adds prepare-next-snapshot-version CI job (#102) via JayShortway (@JayShortway)
* Copies some CI jobs from GitHub Actions to CircleCI. (#101) via JayShortway (@JayShortway)
* Moves publishing logic from the CircleCI config to Fastlane. (#100) via JayShortway (@JayShortway)
* Release train (#99) via JayShortway (@JayShortway)
* Adds the upgrade-hybrid-common action (#93) via JayShortway (@JayShortway)
* Adds Fastlane (#90) via JayShortway (@JayShortway)
* Adds the Danger bot. (#87) via JayShortway (@JayShortway)
* Corrects the artifactId of the :paywalls module. (#86) via JayShortway (@JayShortway)
* Adds snapshot publishing (#83) via JayShortway (@JayShortway)
* Adds note on migration to README.md (#79) via JayShortway (@JayShortway)
* Update README.md (#78) via JayShortway (@JayShortway)
* Adds Paywalls (#77) via JayShortway (@JayShortway)
* Adds issue and PR templates. via JayShortway (@JayShortway)
* Fixes docs for StoreProduct.presentedOfferingContext. via JayShortway (@JayShortway)
* Adds a currently-disabled check for the PHC version on iOS. via JayShortway (@JayShortway)
* Minor consistency update. via JayShortway (@JayShortway)
* PlatformInfo is properly passed on Android and iOS. via JayShortway (@JayShortway)
* Adds klib abi validation. via JayShortway (@JayShortway)
* Now depends on PurchasesHybridCommon (#70) via JayShortway (@JayShortway)
