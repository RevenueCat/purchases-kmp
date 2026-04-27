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

## Dependencies

`projects.core` and `projects.revenuecatui` are referenced as Gradle project
dependencies, so the E2E tests always exercise the code on the current branch.
On iOS, `projects.knUi` is also used for the native modal paywall presentation.
