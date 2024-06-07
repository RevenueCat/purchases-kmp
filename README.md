<h3 align="center">😻 In-App Subscriptions Made Easy 😻</h3>  
  
![GitHub Release](https://img.shields.io/github/v/release/JayShortway/kobankat) 
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/JayShortway/kobankat/main.yml)

## Note   
 
This is the official [RevenueCat](https://www.revenuecat.com/) SDK for Kotlin Multiplatform, supporting Android and iOS. It started out as an independent project named 'KobanKat' by [@JayShortway](https://github.com/JayShortway). We're currently in the process of migrating everything over. This README will be updated as we go. For now, the below instructions are still valid. 

## Getting started 

### Adding the dependency
KobanKat is available on Maven Central. Add the following coordinates to your `build.gradle[.kts]` or `libs.versions.toml`, and then add KobanKat as a dependency to your `commonMain` source set.  
```toml
[versions]
kobankat = "<version>"

[libraries]
shortway-kobankat-core = { module = "io.shortway.kobankat:kobankat-core", version.ref = "kobankat" }

# Optional: adds suspending functions that return kotlin.Result to indicate success / failure.
shortway-kobankat-result = { module = "io.shortway.kobankat:kobankat-result", version.ref = "kobankat" }

# Optional: adds suspending functions that return Arrow's Either to indicate success / failure.
shortway-kobankat-either = { module = "io.shortway.kobankat:kobankat-either", version.ref = "kobankat" }

# Optional: adds extension properties representing timestamps as kotlinx-datetime Instants.
shortway-kobankat-datetime = { module = "io.shortway.kobankat:kobankat-datetime", version.ref = "kobankat" }
```
See [Releases](../../releases) for the latest version.  

You might also need to add this compiler flag in your `build.gradle[.kts]`:
```kotlin
kotlin {
  // ...
  sourceSets {
    all {
      languageSettings.apply {
        if (name.lowercase().startsWith("ios")) {
          optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
      }
    }
    // ...
  }
  // ...
}
```

Since KobanKat depends on the official RevenueCat SDKs, we need to tell Xcode about the iOS SDK. 
1. Make sure you have [CocoaPods](https://cocoapods.org/) installed.
2. `cd` into your `iosApp` folder (containing your `.xcodeproj` and/or `.xcworkspace` file(s)).
3. Initialize CocoaPods:
   ```shell
   pod init
   ```
4. Open the newly created `Podfile` in your favorite text editor, and add the following line:
   ```ruby
   pod 'RevenueCat'
   ```
5. Save the file and run:
   ```shell
   pod install
   ```
6. If you didn't have an `.xcworkspace` file before, you'll have one now. Open it in Xcode, and your app should build. 

### Initializing the SDK
To instantiate the SDK, do the following:
1. On Android only, call `PurchasesFactory.setApplication()` in `Application.onCreate()`.
2. In your common code, call `PurchasesFactory.configure()`. 
  
After this, you can access the SDK's singleton instance using `PurchasesFactory.sharedInstance`. This process is analogous to the [other SDKs](https://www.revenuecat.com/docs/getting-started/configuring-sdk).

### Sample
An example implementation is provided in the `composeApp` and `iosApp` folders. 

### Version
The versioning scheme is in the form `X-Y-Z`, where:
* `X` is the KobanKat version.
* `Y` is the RevenueCat Android version that is being tracked.
* `Z` is the RevenueCat iOS version that is being tracked.

## Compatibility 
KobanKat supports Android and iOS targets for now. Most types are aliased to the respective official SDK types, so add on libraries like the official Paywalls SDK are compatible with KobanKat. 

## Public API
KobanKat's public API is intended to stay as close as possible to RevenueCat's Android, iOS and other hybrid SDKs. This means it should be a near drop-in replacement. The namespace has changed from `com.revenuecat.purchases` to `io.shortway.kobankat`. The full API reference is available [here](https://revenuecat.github.io/purchases-kmp/). 

## What's with the name?
The name, _KobanKat_, is an homage to the original revenue cat: [Meowth](https://bulbapedia.bulbagarden.net/wiki/Meowth_(Pok%C3%A9mon)). 😸 This Pokémon is is attracted to round and shiny objects, can even generate revenue at will with its Pay Day move, and has a gold coin embedded in its forehead. This coin is a so-called [_koban_](https://en.wikipedia.org/wiki/Koban_(coin)) coin, and that's where the name of this library comes from. ("Cat" has to be spelled with a 'K' of course. It's a Kotlin library after all.)
