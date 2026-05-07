## RevenueCat SDK
### 💥 Breaking Changes
* This release updates to Billing Library 8.3.0 with min SDK supported of Android 6 (API 23), previously min was 21. It also removes a previous workaround used to be able to restore consumed one time products which is not available anymore.
* This release removes the need to specify a version of purchases-hybrid-common. See our [migration guide](migrations/3.0.0-MIGRATION.md) for how to do this.
* Removal of purchases-kmp-datetime dependency 
* Updates Kotlin to version 2.3.20

For more information, please check our [migration guide](migrations/3.0.0-MIGRATION.md)

### ✨ New Features
* Add PostHog user ID setter (#837) via Cesar de la Vega (@vegaro)

### 📦 Dependency Updates
* [RENOVATE] Update dependency gradle to v9.5.0 (#842) via Toni Rico (@tonidero)
* [RENOVATE] Update purchases-android to v10.4.0 (#843) via Toni Rico (@tonidero)
* [RENOVATE] Update dependency upstream/purchases-ios to v5.71.0 (#813) via RevenueCat Git Bot (@RCGitBot)
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 18.3.0 (#834) via RevenueCat Git Bot (@RCGitBot)
* [AUTOMATIC BUMP] Updates purchases-hybrid-common to 18.2.0 (#831) via RevenueCat Git Bot (@RCGitBot)
* [RENOVATE] Update dependency upstream/purchases-ios to v5.67.2 (#805) via RevenueCat Git Bot (@RCGitBot)

### 🔄 Other Changes
* Bump fastlane-plugin-revenuecat_internal from `21e02ec` to `af7bb5c` (#838) via dependabot[bot] (@dependabot[bot])
* Bump fastlane-plugin-revenuecat_internal from `2d11430` to `21e02ec` (#835) via dependabot[bot] (@dependabot[bot])
* Bump fastlane-plugin-revenuecat_internal from `d24ab26` to `2d11430` (#833) via dependabot[bot] (@dependabot[bot])
* Bump fastlane from 2.233.0 to 2.233.1 (#830) via dependabot[bot] (@dependabot[bot])
* Bump fastlane-plugin-revenuecat_internal from `b822f01` to `d24ab26` (#822) via dependabot[bot] (@dependabot[bot])
* Bump fastlane-plugin-revenuecat_internal from `e348913` to `b822f01` (#820) via dependabot[bot] (@dependabot[bot])
* Bump fastlane from 2.232.2 to 2.233.0 (#817) via dependabot[bot] (@dependabot[bot])
* Bump fastlane-plugin-revenuecat_internal from `a1eed48` to `e348913` (#818) via dependabot[bot] (@dependabot[bot])
* Bump fastlane-plugin-revenuecat_internal from `20911d1` to `a1eed48` (#812) via dependabot[bot] (@dependabot[bot])
* Bump fastlane-plugin-revenuecat_internal from `894bb1b` to `20911d1` (#806) via dependabot[bot] (@dependabot[bot])
* Update CODEOWNERS default owner to @RevenueCat/sdk (#803) via Antonio Pallares (@ajpallares)
* Bump fastlane-plugin-revenuecat_internal from `ceecf91` to `894bb1b` (#804) via dependabot[bot] (@dependabot[bot])
* Bump fastlane-plugin-revenuecat_internal from `6289be1` to `ceecf91` (#795) via dependabot[bot] (@dependabot[bot])
* Fix flaky build-libraries-ios by serializing pod installs (#792) via Antonio Pallares (@ajpallares)
