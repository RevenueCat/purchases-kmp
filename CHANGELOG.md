## 1.1.2+13.5.0
## RevenueCat SDK
### 📦 Dependency Updates
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 13.5.0 (#236) via RevenueCat Git Bot (@RCGitBot)
  * [iOS 5.6.0](https://github.com/RevenueCat/purchases-ios/releases/tag/5.6.0)
  * [iOS 5.5.0](https://github.com/RevenueCat/purchases-ios/releases/tag/5.5.0)
* Bump fastlane from 2.223.1 to 2.224.0 (#235) via dependabot[bot] (@dependabot[bot])

### 🔄 Other Changes
* Fixes a typo in the Fastfile (#238) via JayShortway (@JayShortway)
* Attempts to fix ffi by downgrading to 1.16.3. (#234) via JayShortway (@JayShortway)

## 1.1.1+13.4.0
## RevenueCat SDK
### 📦 Dependency Updates
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 13.4.0 (#230) via RevenueCat Git Bot (@RCGitBot)
  * [Android 8.8.0](https://github.com/RevenueCat/purchases-android/releases/tag/8.8.0)
  * [iOS 5.4.0](https://github.com/RevenueCat/purchases-ios/releases/tag/5.4.0)
* Bump fastlane from 2.223.0 to 2.223.1 (#229) via dependabot[bot] (@dependabot[bot])
* Bump fastlane from 2.222.0 to 2.223.0 (#225) via dependabot[bot] (@dependabot[bot])

### 🔄 Other Changes
* Update fastlane plugin (#232) via Toni Rico (@tonidero)
* Adds actionlint to lint GitHub Actions workflows (#227) via JayShortway (@JayShortway)
* Attempts to fix the on_release_tag workflow to allow publishing the API reference site (#228) via JayShortway (@JayShortway)
* Skips publishing SNAPSHOT if the current version is not a SNAPSHOT. (#226) via JayShortway (@JayShortway)

## 1.1.0+13.3.0
## RevenueCat SDK

> [!NOTE]
> It is now possible to integrate `PurchasesHyridCommon[UI]` using Swift Package Manager! Use this URL: https://github.com/RevenueCat/purchases-hybrid-common/

### 🐞 Bugfixes
* Fixes the PurchasesDelegate being deallocated on iOS (#214) via JayShortway (@JayShortway)
### 📦 Dependency Updates
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 13.3.0 (#218) via RevenueCat Git Bot (@RCGitBot)

### 🔄 Other Changes
* Updates Fastlane plugin to 5b2e35c6985e02b5911c53ebe4d071e742e03ccc (#221) via JayShortway (@JayShortway)
* Fixes requirements of the hold job. (#220) via JayShortway (@JayShortway)
* Enables incremental builds per workflow. (#215) via JayShortway (@JayShortway)
* Saves and restores the Kotlin/Native compiler on CI (#210) via JayShortway (@JayShortway)
* Parallelizes CI jobs by platform (#209) via JayShortway (@JayShortway)
* Migrate GitHub Actions to CircleCI (#207) via JayShortway (@JayShortway)

## 1.0.1+13.2.1
## RevenueCat SDK
### 🐞 Bugfixes
* Supports uniquePackageNames by setting the namespace for the :models module (#195) via JayShortway (@JayShortway)
### 📦 Dependency Updates
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 13.2.1 (#206) via RevenueCat Git Bot (@RCGitBot)

### 🔄 Other Changes
* Bump fastlane-plugin-revenuecat_internal from 5140dbc to 55a0455 (#208) via Cesar de la Vega (@vegaro)
* Runs the update-purchases-hybrid-common-version job on macOS. (#205) via JayShortway (@JayShortway)
* Moves PurchasesDelegate to the :core module (#194) via JayShortway (@JayShortway)
* Moves LogHandler and LogLevel to the :core module (#193) via JayShortway (@JayShortway)
* Moves miscellaneous types to the models package (#192) via JayShortway (@JayShortway)
* Moves errors to the models package (#191) via JayShortway (@JayShortway)
* Moves Offerings to the models package (#190) via JayShortway (@JayShortway)
* Moves CustomerInfo to the models package (#189) via JayShortway (@JayShortway)
* Moves most enums to the models package (#188) via JayShortway (@JayShortway)
* Makes issue identifiers clickable in Android Studio (#187) via JayShortway (@JayShortway)
* Make sure the version gets automatically updated in the podspec files too (#197) via JayShortway (@JayShortway)
* Passes the right contexts to the automatic-bump job. (#196) via JayShortway (@JayShortway)
* Tester app shows every Offerings and CustomerInfo property (#178) via JayShortway (@JayShortway)
* Update RELEASING.md (#186) via JayShortway (@JayShortway)
* Update fastlane plugin version (#184) via Cesar de la Vega (@vegaro)
* Update Gemfile to fix dependabot (#183) via Cesar de la Vega (@vegaro)
* Create dependabot.yml (#182) via Cesar de la Vega (@vegaro)

## 1.0.0+13.2.0
### Breaking Changes
* Removes unused Locale (#166) via JayShortway (@JayShortway)
* Do not expose PHC api (#164) (@JayShortway, @tonidero, @vegaro)
* Adopt Latest PHC Version (#139) via Will Taylor (@fire-at-will)
* Removes Offerings.getCurrentOfferingForPlacement() (#121) via JayShortway (@JayShortway)
* Fix iOS StoreProduct description & price (#108) via JayShortway (@JayShortway)
* Uses PurchasesAreCompletedBy (#105) via JayShortway (@JayShortway)
* Remove PurchasesFactory (#103) via JayShortway (@JayShortway)
### New Features
* Adds ProductCategory to StoreProduct (#114) via JayShortway (@JayShortway)
### Bugfixes
* Fixes an incorrect cast to Package. (#176) via JayShortway (@JayShortway)
* Fixes a PaywallListener crash (#170) via JayShortway (@JayShortway)
* Fix iOS crash showing paywall for current offering (#129) via JayShortway (@JayShortway)
* Fixes Compose<->K2 compilation error (#111) via JayShortway (@JayShortway)
* Fixes the Transaction type (#113) via JayShortway (@JayShortway)
* Fixes showInAppMessagesIfNeeded() on iOS (#110) via JayShortway (@JayShortway)
### Dependency Updates
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 13.2.0 (#174) via RevenueCat Git Bot (@RCGitBot)
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 13.1.0 (#156) via RevenueCat Git Bot (@RCGitBot)
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 11.1.1 (#124) via RevenueCat Git Bot (@RCGitBot)
* Consolidates Compose versions (#112) via JayShortway (@JayShortway)
* Checks PHC version at configure() time on iOS (#104) via JayShortway (@JayShortway)
### Other Changes
* Fixes a typo in the Fastfile. (#179) via JayShortway (@JayShortway)
* The :update_hybrid_common lane also updates Podfile.lock. (#175) via JayShortway (@JayShortway)
* Greatly simplifies how Price.amountMicros is determined on iOS. (#172) via JayShortway (@JayShortway)
* Adds v1 migration guide (#171) via JayShortway (@JayShortway)
* :update_hybrid_common lane also updates mappings.podspec and models.podspec (#169) via JayShortway (@JayShortway)
* Updates the KobanKat migration guide (#167) via JayShortway (@JayShortway)
* Calls out WorkManager specifically in the initialization error message. (#155) via JayShortway (@JayShortway)
* Accept Fastlane Changes (#151) via Will Taylor (@fire-at-will)
* Adds the git-town config to source control. (#146) via JayShortway (@JayShortway)
* Appends PHC version to the library version as build metadata (#136) via JayShortway (@JayShortway)
* Adds required Kotlin test dependencies to :core (#142) via JayShortway (@JayShortway)
* Temporarily reverts "Remove expect/actual from enums without extra properties (#137)" (#141) via JayShortway (@JayShortway)
* Remove expect/actual from enums that lack extra properties (#137) via JayShortway (@JayShortway)
* Improves the error message when the PurchasesInitializer is missing from the merged AndroidManifest.xml. (#130) via JayShortway (@JayShortway)
* Fixes viewing revenuecatui Kotlin code in Xcode (#127) via JayShortway (@JayShortway)
* Removes the Compose Compiler Gradle Plugin from :apiTester. (#125) via JayShortway (@JayShortway)
* Adds missing API tests (#106) via JayShortway (@JayShortway)
* Publishes API reference only on release. (#122) via JayShortway (@JayShortway)
* Fixes the release pipeline (#119) via JayShortway (@JayShortway)
* Adds KobanKat migration guide (#116) via JayShortway (@JayShortway)
* Updates README.md (#115) via JayShortway (@JayShortway)
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
* Changes package to com.revenuecat (#80) via JayShortway (@JayShortway)
* Adds note on migration to README.md (#79) via JayShortway (@JayShortway)
* Update README.md (#78) via JayShortway (@JayShortway)
* Adds Paywalls (#77) via JayShortway (@JayShortway)
* Adds issue and PR templates. via JayShortway (@JayShortway)
* Removes StoreProduct.pricePer*() functions, as they are not available in PHC, nor in e.g. Flutter & React Native. via JayShortway (@JayShortway)
* Fixes docs for StoreProduct.presentedOfferingContext. via JayShortway (@JayShortway)
* Adds a currently-disabled check for the PHC version on iOS. via JayShortway (@JayShortway)
* Minor consistency update. via JayShortway (@JayShortway)
* PlatformInfo is properly passed on Android and iOS. via JayShortway (@JayShortway)
* SDK version is now just '0.5.0', without any versions of platform RC SDKs. via JayShortway (@JayShortway)
* SDK3448 PurchasesConfiguration binary compatibility (#72) via JayShortway (@JayShortway)
* Adds klib abi validation. via JayShortway (@JayShortway)
* SDK-3448 configuration consistency (#71) via JayShortway (@JayShortway)
* SDK-3450 poc for using phc (#70) via JayShortway (@JayShortway)
* Update dependency org.jetbrains.kotlinx:kotlinx-datetime to v0.6.0 (#66) via JayShortway (@JayShortway)
* Update kotlin to v1.9.24 (#64) via JayShortway (@JayShortway)
* Update agp to v8.4.1 (#68) via JayShortway (@JayShortway)
* Update plugin jetbrains-compose to v1.6.10 (#67) via JayShortway (@JayShortway)
* Update agp to v8.4.0 (#62) via JayShortway (@JayShortway)
* Update dependency androidx.activity:activity-compose to v1.9.0 (#61) via JayShortway (@JayShortway)
* Update compose to v1.6.7 (#63) via JayShortway (@JayShortway)
* Prepares the next development version. via JayShortway (@JayShortway)

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
