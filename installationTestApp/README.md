# Installation test app

Minimal Android and iOS consumer app used by release CI to verify Maven-published SDK artifacts compile and link like external integrators.

Generated from the [Kotlin Multiplatform Wizard](https://kmp.new/) mobile-shared template. Uses project dependencies locally; consumer CI resolves from Maven Local via `-PusePublishedMavenLocalArtifacts=true`.

## CI usage

1. `validate-publish` runs `publishToMavenLocal` on Xcode 16.4.
2. `validate-android-consumer-link` restores those artifacts and assembles the Android app.
3. `validate-ios-consumer-link` restores those artifacts and builds the iOS app.

## Local usage

```bash
./gradlew publishToMavenLocal
printf '\nusePublishedMavenLocalArtifacts=true\n' >> gradle.properties
./gradlew :installationTestApp:assembleDebug
xcodebuild -project installationTestApp/iosApp/iosApp.xcodeproj -scheme iosApp -sdk iphonesimulator -arch arm64 build
```
