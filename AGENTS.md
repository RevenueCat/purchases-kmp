# purchases-kmp — Development Guidelines

## Project Overview

RevenueCat's official Kotlin Multiplatform (KMP) SDK for in-app purchases and subscriptions. This SDK wraps the native iOS and Android SDKs via `purchases-hybrid-common`, providing a unified Kotlin API that targets both Android and iOS from shared code. UI components use Compose Multiplatform.

**Related repositories:**
- **iOS SDK**: https://github.com/RevenueCat/purchases-ios
- **Android SDK**: https://github.com/RevenueCat/purchases-android
- **Hybrid Common**: https://github.com/RevenueCat/purchases-hybrid-common — shared native layer that bridges platform SDKs to hybrid frameworks. On Android it is consumed as a Maven dependency; on iOS it is consumed via CocoaPods (`PurchasesHybridCommon` / `PurchasesHybridCommonUI`).

When implementing features or debugging, check these repos for reference and patterns.

## Important: Public API Stability

**Do NOT introduce breaking changes to the public API.** Published modules (`core`, `models`, `datetime`, `either`, `result`, `revenuecatui`) are consumed by apps via Maven Central.

Safe changes:
- Adding new optional parameters to existing methods
- Adding new classes, methods, or properties
- Bug fixes that don't change method signatures
- Internal implementation changes

Requires explicit approval:
- Removing or renaming public classes/methods/properties
- Changing method signatures (parameter types, required params)
- Changing return types
- Modifying behavior in ways that break existing integrations

Binary compatibility is enforced by `kotlinx.binary-compatibility-validator` (the `apiCheck` Gradle task). The `apiTester` module compiles against the full public API in CI. **If either fails, you've likely broken the public API.**

All published library modules use `explicitApi()`, so every public declaration must have an explicit visibility modifier.

## Code Structure

```
purchases-kmp/
├── core/                # Main SDK entry point (Purchases class, configuration, etc.)
│   └── src/
│       ├── commonMain/  # Shared API: expect classes (Purchases, AdTracker), extensions
│       ├── androidMain/ # actual implementations wrapping purchases-hybrid-common (Android)
│       ├── iosMain/     # actual implementations wrapping PurchasesHybridCommon (iOS via CocoaPods)
│       ├── commonTest/  # Shared unit tests
│       ├── androidUnitTest/
│       └── iosTest/
├── models/              # Data models (commonMain only, no expect/actual)
├── mappings/            # Internal mapping layer between native SDK types and KMP models
│   └── src/
│       ├── commonMain/  # Shared mapping interfaces
│       ├── androidMain/ # Android-specific mappings from Java/Kotlin SDK types
│       ├── iosMain/     # iOS-specific mappings from Objective-C/Swift SDK types
│       └── *Test/       # Mapping tests (includes androidInstrumentedTest)
├── revenuecatui/        # Compose Multiplatform paywall UI components
│   └── src/
│       ├── commonMain/  # Shared Compose UI (Paywall composables)
│       ├── androidMain/ # Android-specific UI wrapping purchases-hybrid-common-ui
│       └── iosMain/     # iOS-specific UI wrapping PurchasesHybridCommonUI
├── datetime/            # kotlinx-datetime extensions for core types
├── either/              # Arrow Either extensions for core types
├── result/              # Kotlin Result extensions for core types
├── apiTester/           # Compile-only module that exercises the full public API surface
├── composeApp/          # Sample Compose Multiplatform app (Android + iOS)
├── iosApp/              # iOS Xcode project for running the sample app
├── build-logic/         # Gradle convention plugins
│   └── convention/      # revenuecat-library plugin (configures KMP, Android, Detekt, publishing)
├── config/detekt/       # Detekt lint configuration
├── fastlane/            # Release automation (Fastfile)
├── .circleci/           # CI configuration (primary CI system)
├── migrations/          # Version migration guides
├── upstream/            # Git submodule (purchases-ios)
└── gradle/libs.versions.toml  # Version catalog (single source of truth for versions)
```

## Constraints / Support Policy

| Platform | Minimum Version |
|----------|-----------------|
| Kotlin   | 2.1.0+          |
| Java     | 1.8             |
| Android  | SDK 21+ (API 24+ for revenuecatui) |
| iOS      | 13.0+ (15.0+ for revenuecatui) |

Don't raise minimum versions unless explicitly required and justified.

## Build Commands

This project uses Gradle with the Kotlin Multiplatform plugin. A macOS machine with Xcode is required for iOS targets.

```bash
# Build all libraries for Android (Debug + Release)
./gradlew :core:compileDebugKotlinAndroid :core:compileReleaseKotlinAndroid

# Build all libraries for iOS
./gradlew :core:compileKotlinIosArm64 :core:compileKotlinIosSimulatorArm64

# Build sample app (Android)
./gradlew :composeApp:compileDebugKotlinAndroid

# Build sample app (iOS)
./gradlew :composeApp:compileKotlinIosArm64
```

### Linting (Detekt)

```bash
# Detekt on commonMain across all published modules
./gradlew detektCommonMain

# Detekt on all source sets for a specific module
./gradlew :core:detektAll

# Detekt on a specific source set
./gradlew :core:detektCommonMain
./gradlew :core:detektAndroidMain
./gradlew :core:detektIosMain
```

### API Compatibility

```bash
# Check binary compatibility (requires macOS for iOS klib checks)
./gradlew apiCheck

# Dump updated API files after intentional changes
./gradlew apiDump
```

### Testing

```bash
# Android unit tests (all modules)
./gradlew testDebugUnitTest testReleaseUnitTest

# iOS unit tests
./gradlew iosSimulatorArm64Test iosX64Test

# Build-logic unit tests
./gradlew :build-logic:convention:test

# Single module tests
./gradlew :core:testDebugUnitTest
./gradlew :mappings:testDebugUnitTest
```

### Documentation

```bash
# Generate Dokka HTML documentation
./gradlew dokkatooGeneratePublicationHtml
```

### Publishing (local)

```bash
# Publish to Maven Local for local testing
./gradlew publishToMavenLocal
```

## Build System

### Convention Plugins (`build-logic/`)

The `revenuecat-library` convention plugin is applied to all library modules. It configures:
- Kotlin Multiplatform targets: `androidTarget()`, `iosX64()`, `iosArm64()`, `iosSimulatorArm64()`
- Android library defaults (compileSdk, minSdk, Java compatibility)
- `explicitApi()` mode
- `-Xexpect-actual-classes` compiler flag
- `@OptIn(kotlinx.cinterop.ExperimentalForeignApi)` for iOS source sets
- Detekt, Dokka, and Maven Publish plugins
- Swift/CocoaPods dependency handling for iOS

Each module's `build.gradle.kts` applies `id("revenuecat-library")` and only needs to declare its specific dependencies and CocoaPods configuration.

### Version Catalog (`gradle/libs.versions.toml`)

All dependency versions are managed centrally. Key versions:
- `revenuecat-kmp`: The SDK version itself
- `revenuecat-common`: The `purchases-hybrid-common` version (updated via automated PRs)
- `kotlin`: Kotlin compiler version
- `compose`: Jetbrains Compose version

### Gradle Properties

Configuration caching and build caching are enabled. CInterop commonization is enabled for iOS targets.

## KMP-Specific Guidance

### expect/actual Pattern

The SDK uses `expect`/`actual` declarations for classes that wrap platform-specific native SDK types:

- **`commonMain`**: Declare `expect class` with the public API surface. Example: `Purchases`, `AdTracker` in `core/src/commonMain/`.
- **`androidMain`**: Provide `actual class` wrapping the Java/Kotlin native SDK type from `purchases-hybrid-common`.
- **`iosMain`**: Provide `actual class` wrapping the Objective-C type from `PurchasesHybridCommon` (accessed via CocoaPods cinterop).

File naming convention: `ClassName.kt` for expect, `ClassName.android.kt` and `ClassName.ios.kt` for actual implementations.

### Shared Models vs. Platform-Specific Code

- **`models/`**: Pure `commonMain` data classes with no platform-specific code. These define the SDK's public data types.
- **`mappings/`**: Internal module that converts between native SDK types and KMP model types. Has `androidMain` and `iosMain` source sets. Not published to Maven Central.
- **`core/`**: The main SDK module. Uses `expect`/`actual` for the `Purchases` entry point. Depends on `models` (API) and `mappings` (implementation).

### iOS / CocoaPods

iOS dependencies are consumed via CocoaPods (`PurchasesHybridCommon`, `PurchasesHybridCommonUI`). The build-logic handles Swift dependency configuration and cinterop `.def` file generation.

The `upstream/` directory contains a git submodule pointing to `purchases-ios`.

CocoaPods must be installed before building iOS targets:
```bash
cd iosApp && bundle exec pod install --repo-update
```

### Extension Modules

`datetime`, `either`, and `result` are pure `commonMain` extension modules that add convenience APIs on top of `core`:
- `datetime`: Wraps temporal types with `kotlinx-datetime`
- `either`: Wraps error-returning APIs with Arrow's `Either`
- `result`: Wraps error-returning APIs with Kotlin's `Result`

These modules have no platform-specific code.

### Compose Multiplatform (`revenuecatui`)

The `revenuecatui` module provides paywall UI using Compose Multiplatform. On Android it wraps `purchases-hybrid-common-ui`; on iOS it wraps `PurchasesHybridCommonUI` via CocoaPods. The artifact ID is `purchases-kmp-ui` (not `purchases-kmp-revenuecatui`).

## CI/CD

CI runs on CircleCI (`.circleci/config.yml`). The pipeline includes:
- **Detekt** lint on `commonMain`
- **Binary compatibility validation** (`apiCheck`)
- **Library builds** for Android and iOS
- **Sample app builds** for Android and iOS
- **Public API tests** (compiles `apiTester` for both platforms)
- **Unit tests** for Android, iOS, and build-logic
- **Publishing** to Maven Central (snapshots on every merge, releases on tags)

Releases are managed via Fastlane. See `RELEASING.md` for the full release process.

## Code Conventions

### Kotlin
- Match existing style and patterns exactly
- All public declarations require explicit visibility modifiers (`explicitApi()` is enforced)
- Use the package `com.revenuecat.purchases.kmp` as the base package
- Subpackages by module: `.models`, `.mappings`, `.ui.revenuecatui`, etc.
- Run Detekt before committing

### Adding New Public API
1. Add the `expect` declaration in `commonMain` following existing patterns
2. Add `actual` implementations in both `androidMain` and `iosMain`
3. If the feature involves new data types, add them to `models/` (commonMain only)
4. If the feature requires mapping native types, add mappings in `mappings/`
5. Update `apiTester/` to cover the new API (both `commonMain` and platform source sets)
6. Run `./gradlew apiDump` to update the API compatibility baseline
7. Ensure it's additive (no breaking changes)

### Platform Code
- Keep platform logic isolated in `androidMain`/`iosMain` source sets
- Maintain parity across Android and iOS
- Check the native SDKs (`purchases-android`, `purchases-ios`) for implementation reference
- Use the `mappings` module for converting between native and KMP types

## PR Labels

Every PR must have exactly one of these labels:
- **`pr:feat`** — A new feature
- **`pr:fix`** — A bug fix
- **`pr:RevenueCatUI`** — Changes to the `revenuecatui` module
- **`pr:other`** — Anything else (docs, refactors, CI changes, etc.)

Apply the label when creating the PR via `gh pr create --label <label>`.

## PR Checklist

- [ ] `./gradlew detektCommonMain` passes
- [ ] `./gradlew apiCheck` passes (run `apiDump` if API was intentionally changed)
- [ ] Unit tests pass for affected modules
- [ ] Sample app builds for both platforms
- [ ] If public API changed: confirmed additive and non-breaking
- [ ] If public API changed: `apiTester` updated
- [ ] If behavior changed: docs/changelog updated
- [ ] PR description includes: what, why, how tested

## When the Task is Ambiguous

1. Search for similar existing implementation in this repo first
2. Check `purchases-android`, `purchases-ios`, and `purchases-hybrid-common` for patterns
3. If there's a pattern, follow it exactly
4. If not, propose options with tradeoffs and pick the safest default

## Guardrails

- **Don't invent APIs or file paths** — verify they exist
- **Don't remove code you don't understand** — ask for context
- **Don't make large refactors** unless explicitly requested
- **Keep diffs minimal** — preserve existing formatting
- **Don't break the public API** — if `apiCheck` or `apiTester` fails, investigate why
- **Check native SDKs** when unsure about implementation details
- **Never commit Claude-related files** — do not stage or commit `.claude/` directory, `settings.local.json`, or any AI tool configuration files
- **Never commit API keys or secrets** — do not stage or commit API keys, tokens, credentials, or any sensitive data
