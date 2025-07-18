<h3 align="center">😻 In-App Subscriptions Made Easy 😻</h3>  
  
![GitHub Release](https://img.shields.io/github/v/release/JayShortway/kobankat) 
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/JayShortway/kobankat/main.yml)

RevenueCat is a powerful, reliable, and free to use in-app purchase server with cross-platform support. Our open-source framework provides a backend and a wrapper around StoreKit and Google Play Billing to make implementing in-app purchases and subscriptions easy. 

Whether you are building a new app or already have millions of customers, you can use RevenueCat to:

  * Fetch products, make purchases, and check subscription status with our [native SDKs](https://docs.revenuecat.com/docs/installation). 
  * Host and [configure products](https://docs.revenuecat.com/docs/entitlements) remotely from our dashboard. 
  * Analyze the most important metrics for your app business [in one place](https://docs.revenuecat.com/docs/charts).
  * See customer transaction histories, chart lifetime value, and [grant promotional subscriptions](https://docs.revenuecat.com/docs/customers).
  * Get notified of real-time events through [webhooks](https://docs.revenuecat.com/docs/webhooks).
  * Send enriched purchase events to analytics and attribution tools with our easy integrations.

Sign up to [get started for free](https://app.revenuecat.com/signup).

## Purchases

*Purchases* is the client for the [RevenueCat](https://www.revenuecat.com/) subscription and purchase tracking system. It is an open source framework that provides a wrapper around `BillingClient`, `StoreKit` and the RevenueCat backend to make implementing in-app subscriptions in Kotlin Multiplatform easy - receipt validation and status tracking included!

## Migrating from KobanKat

This SDK started out as an independent project named _KobanKat_, built
by [@JayShortway](https://github.com/JayShortway). If you're currently using KobanKat, check out
our [migration guide](./migrations/KobanKat-MIGRATION.md)

## RevenueCat SDK Features
|   | RevenueCat |
| --- | --- |
✅ | Server-side receipt validation
➡️ | [Webhooks](https://docs.revenuecat.com/docs/webhooks) - enhanced server-to-server communication with events for purchases, renewals, cancellations, and more
📱 | Android and iOS support
🎯 | Subscription status tracking - know whether a user is subscribed whether they're on iOS, Android or web
📊 | Analytics - automatic calculation of metrics like conversion, mrr, and churn
📝 | [Online documentation](https://docs.revenuecat.com/docs) and [SDK Reference](https://revenuecat.github.io/purchases-kmp/) up to date
🔀 | [Integrations](https://www.revenuecat.com/integrations) - over a dozen integrations to easily send purchase data where you need it
💯 | Well maintained - [frequent releases](https://github.com/RevenueCat/purchases-kmp/releases)
📮 | Great support - [Contact us](https://revenuecat.com/support)

## Getting Started
For more detailed information, you can view our complete documentation at [docs.revenuecat.com](https://docs.revenuecat.com/docs).

Please follow the [Quickstart Guide](https://docs.revenuecat.com/docs/) for more information on how to install the SDK.

## Codelab

This codelab is a step-by-step tutorial designed to help you learn and master the [RevenueCat SDK](https://www.revenuecat.com/docs/welcome/overview) taking you from the absolute basics to more advanced implementation. Whether you're just getting started or looking to deepen your understanding, this guide walks you through everything you need to go from zero to hero with RevenueCat.

1. [RevenueCat Google Play Integration](https://revenuecat.github.io/codelab/google-play/codelab-1-google-play-integration/index.html#0): In this codelab, you'll learn how to:

   - Properly configure products on Google Play.
   - Set up the RevenueCat dashboard and connect it to your Google Play products.
   - Understanding Product, Offering, Package, and Entitlement.
   - Create paywalls using the [Paywall Editor](https://www.revenuecat.com/docs/tools/paywalls/creating-paywalls#using-the-editor).

2. [Kotlin Multiplatform Purchases & Paywalls Overview](https://revenuecat.github.io/codelab/kmp/codelab-3-kmp-sdk/index.html#0): In this codelab, you will:

   - Integrate the RevenueCat SDK into your Kotlin Multiplatform project
   - Implement in-app purchases in your KMP application
   - Learn how to distinguish between paying and non-paying users
   - Build a paywall screen, which is based on the server-driven UI approach

## Requirements
- Java 8+
- Kotlin 2.1.0+
- Android 5.0+ (API level 21+)
- iOS 13.0+ 

## SDK Reference
Our full SDK reference [can be found here](https://revenuecat.github.io/purchases-kmp/).
