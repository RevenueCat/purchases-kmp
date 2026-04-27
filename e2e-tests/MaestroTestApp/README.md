# Maestro E2E Test App

A minimal Kotlin Multiplatform (Compose Multiplatform) app used by Maestro end-to-end
tests to verify RevenueCat SDK integration.

## Prerequisites

- JDK 21+
- Xcode (iOS) / Android Studio (Android)
- [Maestro](https://maestro.mobile.dev/) CLI

## Running Locally

```bash
# Android — build the debug APK
./gradlew :e2e-tests:MaestroTestApp:assembleDebug

# iOS — build Kotlin framework, then open in Xcode
./gradlew :e2e-tests:MaestroTestApp:linkDebugFrameworkIosSimulatorArm64
open e2e-tests/MaestroTestApp/iosApp/iosApp.xcodeproj
```

## API Key

The app initialises RevenueCat with the placeholder `MAESTRO_TESTS_REVENUECAT_API_KEY`.
In CI, the Fastlane lane replaces this placeholder with the real key from the
`RC_E2E_TEST_API_KEY_PRODUCTION_TEST_STORE` environment variable (provided by the
CircleCI `e2e-tests` context) before building.

To run locally, either:
- Replace the placeholder in `src/commonMain/kotlin/.../App.kt` with a valid API key
  (do **not** commit it), or
- Export the env var and run the same `sed` command the Fastlane lane uses.

## RevenueCat Project

The test uses a RevenueCat project configured with:
- A **V2 Paywall** (the test asserts "Paywall V2" is visible)
- A `pro` entitlement (the test checks entitlement status after purchase)
- The **Test Store** environment for purchase confirmation

## Launch Arguments

Maestro passes `e2e_test_flow` as a `launchApp` argument to navigate directly to a
specific test case screen, bypassing the Test Cases list. On Android this is read from
intent extras; on iOS from `UserDefaults`.

When no argument is set (e.g. running locally without Maestro), the app shows the
manual Test Cases list.

## Adding a New Test Case

1. Create a screen composable in `src/commonMain/kotlin/.../` (e.g. `YourTestScreen.kt`).
2. Add an entry to `TEST_CASES` in `TestCases.kt`, keyed by the Maestro `e2e_test_flow`
   argument value.
3. Create a Maestro YAML in `e2e-tests/maestro/e2e_tests/` with
   `launchApp.arguments.e2e_test_flow` matching the key from step 2.

This mirrors the pattern used by `purchases-capacitor` and `purchases-flutter`.

## Dependencies

`projects.core` and `projects.revenuecatui` are referenced as Gradle project
dependencies, so the E2E tests always exercise the code on the current branch.
