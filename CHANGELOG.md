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
