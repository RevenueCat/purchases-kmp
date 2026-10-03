<!-- Created by Antonio Pallares. Copyright (c) 2026 RevenueCat, Inc. -->

# SDK update tests

This small Compose app uses the KMP SDK on Android and iOS. Both variants have application identifier
`com.revenuecat.SDKUpdateTester`. The released variant resolves `purchases-kmp-core` from Maven Central;
the local variant depends on `:core`. Each retains its own native dependencies. The build lanes verify
this selection and save the resolved RevenueCat dependencies next to `version.txt`. The on-screen
version comes from KMP's `Purchases.frameworkVersion`.

The seven flows in `../maestro/sdk_update_tests` are byte-for-byte copies of
[purchases-ios#7900](https://github.com/RevenueCat/purchases-ios/pull/7900) and
[purchases-android#4381](https://github.com/RevenueCat/purchases-android/pull/4381).
The runner and release discovery come from
[shared actions#161](https://github.com/RevenueCat/fastlane-plugin-revenuecat_internal/pull/161).
Remove the temporary plugin pin after that PR merges.

## Run locally

Set `MAESTRO_TEST_STORE_API_KEY` in the environment to the Workflows Test Store project's key.
CI uses `WORKFLOWS_TEST_STORE_API_KEY` from the `maestro` context, as in purchases-android.
Its `no_paywall` offering's `$rc_monthly` package is `pro_monthly_subscription`, granting `pro`.
Keys are injected into generated sources at build time. Do not upload APKs, apps, frameworks,
Gradle build outputs, or Xcode derived data containing them.

With one Android emulator or iOS simulator booted, run (replace `android` with `ios` for iOS):

```sh
bundle exec fastlane build_sdk_update_test_apps platform:android
bundle exec fastlane run_sdk_update_test platform:android test_case:anonymous_user
bundle exec fastlane run_sdk_update_test platform:android test_case:logged_in_user
```

`release_version:3.11.0` overrides release discovery for reproduction. By default, the shared action
selects the highest stable purchases-kmp release below the checkout's version. Apps are saved separately
in `build/sdk_update_tests/<platform>/{release,local}`. Android debug builds use the same default signing
key and version codes 1 and 2. The runner retries each case from a clean state, installs the local app
over the released app preserving data, and saves diagnostic output plus final-attempt JUnit reports in
`fastlane/test_output/sdk_update_tests/<platform>/<case>`. iOS cleanup also resets the simulator keychain.

## Coverage limitation

The app fetches customer info on entering the purchase screen and updates it from the purchase result.
An online customer-info refresh can mask a lost entitlement cache by recovering entitlements from the
backend. These shared flows check retained identity and visible entitlements after the update, but do
not prove offline cache preservation. Stronger offline/cache assertions should be agreed across iOS,
Android, and KMP under [SDK-4526](https://linear.app/revenuecat/issue/SDK-4526) and mirrored in all native
implementations; keep the shared YAML unchanged until then.
