# KobanKat
![GitHub Release](https://img.shields.io/github/v/release/JayShortway/kobankat) 
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/JayShortway/kobankat/main.yml)

KobanKat is an unofficial [RevenueCat](https://www.revenuecat.com/) SDK for Kotlin Multiplatform, supporting Android and iOS. 

## Coordinates
KobanKat is published to Maven Central at the following coordinates:
```
io.shortway.kobankat:kobankat-core:<version>
```
Add this as a dependency to your `commonMain` source set. 

### Version
See [Releases](../../releases) for the latest version. The versioning scheme is in the form `X-Y-Z`, where:
* `X` is the KobanKat version.
* `Y` is the RevenueCat Android version that is being tracked.
* `Z` is the RevenueCat iOS version that is being tracked.

## Getting started
To instantiate the SDK, call `PurchasesFactory.configure()` once on each respective platform. After this, you can access the SDK's singleton instance using `PurchasesFactory.sharedInstance`. This process is analogous to the [official SDK](https://www.revenuecat.com/docs/getting-started/configuring-sdk).

## Compatibility 
KobanKat supports Android and iOS targets for now. Most types are aliased to the respective official SDK types, so add on libraries like the official Paywalls SDK are compatible with KobanKat. 

## Public API
KobanKat's public API is intended to stay as close as possible to RevenueCat's official Android, iOS and hybrid SDKs. This means it should be a near drop-in replacement. The namespace has changed from `com.revenuecat.purchases` to `io.shortway.kobankat`. The full API reference is available at [kobankat.shortway.io](https://kobankat.shortway.io/). 

## What's with the name?
The name, _KobanKat_, is an homage to the original revenue cat: [Meowth](https://bulbapedia.bulbagarden.net/wiki/Meowth_(Pok%C3%A9mon)). 😸 This Pokémon is is attracted to round and shiny objects, can even generate revenue at will with its Pay Day move, and has a gold coin embedded in its forehead. This coin is a so-called [_koban_](https://en.wikipedia.org/wiki/Koban_(coin)) coin, and that's where the name of this library comes from. ("Cat" has to be spelled with a 'K' of course. It's a Kotlin library after all.)

## Hi RevenueCat team 👋  
An official RevenueCat Kotlin Multiplatform SDK could be so much better than KobanKat, especially in terms of engineering efficiency. I'd love to work with you on this (or on any other RevenueCat SDK for that matter). Reach out to me at jay [at] shortway [dot] io, and let's talk! 
