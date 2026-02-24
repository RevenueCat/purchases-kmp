# purchases-kmp — Development Guidelines

This file provides guidance to AI coding agents when working with code in this repository.

## Project Overview

RevenueCat's official Kotlin Multiplatform (KMP) SDK for in-app purchases and subscriptions. Enables code sharing between iOS and Android platforms using Kotlin Multiplatform.

**Related repositories:**
- **iOS SDK**: https://github.com/RevenueCat/purchases-ios
- **Android SDK**: https://github.com/RevenueCat/purchases-android
- **Hybrid Common**: https://github.com/RevenueCat/purchases-hybrid-common — Native bridge layer

When implementing features or debugging, check these repos for reference and patterns.

## Important: Public API Stability

**Do NOT introduce breaking changes to the public API.** The SDK is used by production apps.

**Safe changes:**
- Adding new optional parameters to existing methods
- Adding new classes, methods, or properties
- Bug fixes that don't change method signatures
- Internal implementation changes

**Requires explicit approval:**
- Removing or renaming public classes/methods/properties
- Changing method signatures (parameter types, required params)
- Changing return types
- Modifying behavior in ways that break existing integrations

The project uses **kotlinx.binary-compatibility-validator** for API stability checking.

## Code Structure

```
purchases-kmp/
├── core/                     # Main SDK entry point (Purchases class, configuration)
│   └── src/
│       ├── commonMain/       # Shared Kotlin code
│       ├── androidMain/      # Android-specific implementations
│       └── iosMain/          # iOS-specific implementations (Swift interop)
├── models/                   # Shared data models and domain objects
├── mappings/                 # Platform-specific mappings
├── revenuecatui/             # Jetpack Compose UI components for paywalls
├── datetime/                 # KMP datetime utilities
├── either/                   # Either/Result type implementations
├── result/                   # Result wrapper types
├── build-logic/              # Custom Gradle build convention plugins
├── composeApp/               # KMP sample application
├── apiTester/                # API testing application
├── iosApp/                   # iOS demo application
└── fastlane/                 # Release automation
```

## Common Development Commands

```bash
# Build all modules
./gradlew build

# Run all tests
./gradlew test

# Run Detekt linting
./gradlew detektAll

# Generate documentation
./gradlew dokkatooGenerate

# Assemble without tests
./gradlew assemble

# Publish locally
./gradlew publishToMavenLocal
```

### Platform-Specific Tests

```bash
# Common KMP tests
./gradlew commonTest

# Android unit tests
./gradlew androidUnitTest

# iOS tests (requires macOS)
./gradlew iosTest
```

## Project Architecture

### Main Entry Point: `Purchases` Class
**Location**: `core/src/commonMain/kotlin/com/revenuecat/purchases/kmp/Purchases.kt`

- **Singleton Pattern**: `Purchases.sharedInstance` (set via `configure()`)
- **Initialization**: `Purchases.configure(PurchasesConfiguration)` — must be called early in app lifecycle
- **Configuration Builder**: `PurchasesConfiguration.Builder(apiKey)` — fluent builder pattern

### Configuration Options
- `apiKey` (required) — RevenueCat API key
- `appUserId` (optional) — User identifier
- `purchasesAreCompletedBy` — Whether RevenueCat or app completes purchases
- `storeKitVersion` — iOS StoreKit 1 vs 2 selection
- `showInAppMessagesAutomatically` — Control of native in-app messages
- `verificationMode` — Entitlement verification settings

### Module Dependencies

```
:revenuecatui --> :mappings, :core, :models
:core --> :models, :mappings
:mappings --> :models
```

### Source Set Organization

Each module follows standard KMP structure:
```
module/src/
├── commonMain/       # Shared Kotlin code (Android + iOS)
├── commonTest/       # Shared tests
├── androidMain/      # Android-specific implementations
├── androidUnitTest/  # Android unit tests
├── iosMain/          # iOS-specific implementations
└── iosTest/          # iOS tests
```

## Constraints / Support Policy

| Platform | Minimum Version |
|----------|-----------------|
| Kotlin | 2.1.21 |
| Android | minSdk 21, compileSdk 35 |
| iOS (Core) | 13.0+ |
| iOS (UI) | 15.0+ |
| Java | 8+ |

Don't raise minimum versions unless explicitly required and justified.

## Testing

```bash
# All tests
./gradlew test

# Detekt linting
./gradlew detektAll

# API compatibility check
# (uses kotlinx.binary-compatibility-validator)
```

## Development Workflow

1. Install sdkman and run `sdk env install` in project root
2. Build: `./gradlew build`
3. Make changes following KMP source set conventions
4. Run tests: `./gradlew test`
5. Run linting: `./gradlew detektAll`
6. Verify documentation: `./gradlew dokkatooGenerate`

## Pull Request Labels

When creating a pull request, **always add one of these labels** to categorize the change:

| Label | When to Use |
|-------|-------------|
| `pr:feat` | New user-facing features or enhancements |
| `pr:fix` | Bug fixes |
| `pr:other` | Internal changes, refactors, CI, docs, or anything that shouldn't trigger a release |

## When the Task is Ambiguous

1. Search for similar existing implementation in this repo first
2. Check purchases-ios and purchases-android for native SDK patterns
3. Check purchases-hybrid-common for bridge layer patterns
4. If there's a pattern, follow it exactly
5. If not, propose options with tradeoffs and pick the safest default

## Guardrails

- **Don't invent APIs or file paths** — verify they exist before referencing them
- **Don't remove code you don't understand** — ask for context first
- **Don't make large refactors** unless explicitly requested
- **Keep diffs minimal** — only touch what's necessary, preserve existing formatting
- **Don't break the public API** — binary compatibility validator will catch issues
- **Follow KMP conventions** — put shared code in `commonMain`, platform-specific in `androidMain`/`iosMain`
- **Run Detekt** before committing (`./gradlew detektAll`)
- **Check both platforms** — changes in `commonMain` affect both iOS and Android
- **Never commit API keys or secrets** — do not stage or commit credentials or sensitive data
