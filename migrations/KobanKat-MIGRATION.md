# KobanKat Migration Guide
The RevenueCat SDK for Kotlin Multiplatform started out as an independent project named _KobanKat_, built by [@JayShortway](https://github.com/JayShortway). Follow this migration guide if you're currently using KobanKat.

## Maven coordinates
The Maven coordinates of the SDK have changed. 

| Old groupId            | New groupId                |
|------------------------|----------------------------|
| `io.shortway.kobankat` | `com.revenuecat.purchases` |

| Old artifactIds     | New artifactIds          |
|---------------------|--------------------------|
| `kobankat-core`     | `purchases-kmp-core`     |
| `kobankat-datetime` | `purchases-kmp-datetime` |
| `kobankat-either`   | `purchases-kmp-either`   |
| `kobankat-result`   | `purchases-kmp-result`   |
| N/A                 | `purchases-kmp-ui`       |


## Linked iOS framework
The linked iOS framework has changed. This has been linked to your iOS project, e.g. in  your `Podfile`, and should be updated.

### When using Paywalls
| Old framework | New framework             |
|---------------|---------------------------|
| `RevenueCat`  | `PurchasesHybridCommonUI` |

### When not using Paywalls
| Old framework | New framework           |
|---------------|-------------------------|
| `RevenueCat`  | `PurchasesHybridCommon` |

## Paywalls
The SDK now has support for Paywalls! You can use them by adding the following Maven coordinates to your depenencies: 

```
com.revenuecat.purchases:purchases-kmp-ui:<version>
```

## Updated Code References
This migration guide has detailed class, property, and method changes.

### Package change
The base package of all classes has changed.

| Old package            | New package                    |
|------------------------|--------------------------------|
| `io.shortway.kobankat` | `com.revenuecat.purchases.kmp` |

You can make this change using a global search & replace. In Android Studio, press CMD/Ctrl+Shift+R and enter the following:

| Search                         | Replace                                |
|--------------------------------|----------------------------------------|
| `import io.shortway.kobankat.` | `import com.revenuecat.purchases.kmp.` |

Then press "Replace All". 

### Initialization
`PurchasesFactory` has been removed. The SDK can be configured using `Purchases.configure()`, which is the same as the RevenueCat SDKs on other platforms. It is no longer necessary to call `setApplication()` in `Application.onCreate()`. 

The `PurchasesConfiguration` class no longer has a constructor, but can be instantiated with a DSL-like approach:

```kotlin
val config = PurchasesConfiguration(apiKey = "<api-key>") {
    appUserId = "<app-user-id>"
    // More configuration options.
}
```

You can also build the configuration and pass it to `configure()` in one go, as follows:

```kotlin
Purchases.configure(apiKey = "<api-key>") {
    appUserId = "<app-user-id>"
    // More configuration options.
}
```

After this, you can access the SDK's singleton instance using `Purchases.sharedInstance`.

### Class/interface changes

| Changed                             |
|-------------------------------------|
| `PurchasesConfiguration`            |

| Renamed from                                 | Renamed to                          |
|----------------------------------------------|-------------------------------------|
| `Purchases.syncObserverModeAmazonPurchase()` | `Purchases.syncAmazonPurchase()`    |
| `StoreProduct.description`                   | `StoreProduct.localizedDescription` |


| Removed                               |
|---------------------------------------|
| `Purchases.finishTransactions`        |
| `PurchasesConfiguration.observerMode` |
| `PurchasesFactory`                    |
| `StoreProduct.pricePerMonth`          |
| `StoreProduct.pricePerWeek`           |
| `StoreProduct.pricePerYear`           |


| Added                                            |
|--------------------------------------------------|
| `ProductCategory`                                |
| `Purchases.purchasesAreCompletedBy`              |
| `PurchasesAreCompletedBy`                        |
| `PurchasesConfiguration.purchasesAreCompletedBy` |
| `StoreProduct.category`                          |

## Reporting undocumented issues:

Feel free to file an issue! [New RevenueCat Issue](https://github.com/RevenueCat/purchases-kmp/issues/new/).