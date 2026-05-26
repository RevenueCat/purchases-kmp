# Installation test app

Minimal iOS consumer app used by release CI to verify Maven-published SDK artifacts link on current Xcode versions.

Generated from the [Kotlin Multiplatform Wizard](https://kmp.new/) mobile-shared template, then trimmed to iOS-only and wired to resolve `purchases-kmp` from Maven Local. Kotlin is pinned to the minimum supported version via the root version catalog (`2.3.20`).

## CI usage

1. `validate-publish` runs `publishToMavenLocal` on Xcode 16.4.
2. `validate-ios-consumer-link` restores those artifacts and builds this app on Xcode 26.5.

## Local usage

```bash
./gradlew publishToMavenLocal
printf '\nusePublishedMavenLocalArtifacts=true\n' >> gradle.properties
xcodebuild -project installationTestApp/iosApp/iosApp.xcodeproj -scheme iosApp -sdk iphonesimulator -arch arm64 build
```
