## 0.5.0
### Other Changes
* Corrects a variable. via JayShortway (@JayShortway)
* Adds empty required markdown files. via JayShortway (@JayShortway)
* Adds some constants. via JayShortway (@JayShortway)
* Adds current_version_number action. via JayShortway (@JayShortway)
* Adds :automatic-bump and :bump lanes. via JayShortway (@JayShortway)
* Adds the release-train CircleCI workflow. via JayShortway (@JayShortway)
* Updates .env.SAMPLE and the fastlane readme. via JayShortway (@JayShortway)
* Adds the upgrade-hybrid-common CI action (#93) via JayShortway (@JayShortway)
* Adds Fastlane (#90) via JayShortway (@JayShortway)
* Adds the Danger bot. (#87) via JayShortway (@JayShortway)
* Corrects the artifactId of the :paywalls module and renames it to :revenuecatui. (#86) via JayShortway (@JayShortway)
* Adds snapshot publishing (#83) via JayShortway (@JayShortway)
* Changes package to com.revenuecat (#80) via JayShortway (@JayShortway)
* Adds note on migration to README.md (#79) via JayShortway (@JayShortway)
* Updates the link to the API reference. (#78) via JayShortway (@JayShortway)
* Adds Paywalls (#77)

* Adds a new :ui module.

* Renames :ui to :paywalls.
Apparently :ui conflicts with compose:ui, causing compile errors.

* Increases heap space to 4 GiB.

* Uses the kobankat-library plugin, and sets the package to ui.revenuecatui for consistency.

* Adds the correct ui dependencies.

* Adds PaywallOptions.

* Adds Paywall.

* Adds PaywallFooter.

* Minimizes dependencies.

* Fixes restoring purchases on Android.

* Fixes toAndroidPaywallOptions().

* Fixes the minSdk for paywalls.

* The sample app can now show paywalls.

* Adds footer paywall buttons.

* Fixes showing footer paywalls.

* Adds the RevenueCatUI pod to iosApp.

* Fixes "unrecognized selector sent to class" by using the correct (PHC) pods in iosApp.

* Removes useless receiver.

* Updates to Kotlin 2.0.0. Can revert later if needed.

* Adds Kotlin code to the Xcode workspace.

* Makes platform-to-common error conversion public. Could be made internal later, with an annotation?

* Adds .kotlin to .gitignore.

* Adds PaywallListener.

* Adds PaywallListener to PaywallOptions.

* Adds shouldDisplayDismissButton to PaywallOptions.

* Adds and uses UIKitPaywall.

* Propagates shouldDisplayDismissButton on Android.

* Deletes PaywallViewControllerDelegate.

* Sets shouldDisplayDismissButton to true.

* Updates some Xcode files.

* Adds a back button to CustomPaywallContent.

* Fixes the height of PaywallFooter by animating it.

* PaywallFooter now also works without a listener set.

* UIKitPaywall uses UIKitViewController instead of UIKitView.

* Adds docs to PaywallListener to fix Detekt.

* Updates public api dump.

* Fixes api-tester.

* Adds 2 run configurations that mimic CI.

* Adds public api dump for paywalls. via JayShortway (@JayShortway)
* Adds issue and PR templates. via JayShortway (@JayShortway)
* Removes StoreProduct.pricePer*() functions, as they are not available in PHC, nor in e.g. Flutter & React Native. via JayShortway (@JayShortway)
* Fixes docs for StoreProduct.presentedOfferingContext. via JayShortway (@JayShortway)
* Adds a currently-disabled check for the PHC version on iOS. via JayShortway (@JayShortway)
* Minor consistency update. via JayShortway (@JayShortway)
* PlatformInfo is properly passed on Android and iOS. via JayShortway (@JayShortway)
* SDK version is now just '0.5.0', without any versions of platform RC SDKs. via JayShortway (@JayShortway)
* SDK-3448 PurchasesConfiguration binary compatibility (#72)

* PurchasesConfiguration is now updatable in a binary compatible way.

* Adds all PurchasesConfiguration parameters from Flutter & React Native, and uses them.

* PurchasesConfiguration constructor no longer has default values. The Builder has.

* Adds documentation to PurchasesConfiguration.Builder.

* Fixes incorrect documentation for PurchasesFactory.configure().

* Removes some redundant qualifiers in PurchasesConfiguration.

* Adds Purchases.showInAppMessagesIfNeeded().

* Renames some members of AndroidProvider.

* Minor documentation update.

* Updates the api dump.

* validate-binary-compatibility CI job now runs on macos-latest, because we are checking native ABIs now. via JayShortway (@JayShortway)
* Adds klib abi validation. via JayShortway (@JayShortway)
* SDK-3448 configuration consistency (#71)

* Adds the App Startup library.

* Adds and uses PurchasesInitializer.

* ActivityProvider is now AndroidProvider.

* Simplifies initialization logic in the sample app.

* Updates core.api. via JayShortway (@JayShortway)
* SDK-3450 Migrates to PHC (#70)

* Replaces platform RevenueCat SDKs with the purchases-hybrid-common SDK.

* Fixes imports using find and replace. (Fails to compile.)

* Fixes compile errors.

* Fixes :apiTester.

* Updates core.api. via JayShortway (@JayShortway)
* Update dependency org.jetbrains.kotlinx:kotlinx-datetime to v0.6.0 (#66)

Co-authored-by: Renovate Bot <bot@renovateapp.com> via JayShortway (@JayShortway)
* Update kotlin to v1.9.24 (#64)

Co-authored-by: Renovate Bot <bot@renovateapp.com> via JayShortway (@JayShortway)
* Update agp to v8.4.1 (#68)

Co-authored-by: Renovate Bot <bot@renovateapp.com> via JayShortway (@JayShortway)
* Update plugin jetbrains-compose to v1.6.10 (#67)

Co-authored-by: Renovate Bot <bot@renovateapp.com> via JayShortway (@JayShortway)
* Update agp to v8.4.0 (#62)

Co-authored-by: Renovate Bot <bot@renovateapp.com> via JayShortway (@JayShortway)
* Update dependency androidx.activity:activity-compose to v1.9.0 (#61)

Co-authored-by: Renovate Bot <bot@renovateapp.com> via JayShortway (@JayShortway)
* Update compose to v1.6.7 (#63)

Co-authored-by: Renovate Bot <bot@renovateapp.com> via JayShortway (@JayShortway)
* Prepares the next development version. via JayShortway (@JayShortway)

