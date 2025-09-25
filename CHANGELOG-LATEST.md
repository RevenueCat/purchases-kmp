> [!WARNING]  
> If you don't have any login system in your app, please make sure your one-time purchase products have been correctly configured in the RevenueCat dashboard as either consumable or non-consumable. If they're incorrectly configured as consumables, RevenueCat will consume these purchases. This means that users won't be able to restore them from version 2.0.0 onward.
> Non-consumables are products that are meant to be bought only once, for example, lifetime subscriptions.


## RevenueCat SDK
### 🐞 Bugfixes
* Fixes a potential crash on Android when calling `purchase()` in rapid succession (#505) via JayShortway (@JayShortway)

### 🔄 Other Changes
* Updates fastlane-plugin-revenuecat_internal to e1c0e04. (#507) via JayShortway (@JayShortway)
* Bump fastlane-plugin-revenuecat_internal from `a6dc551` to `6d539b3` (#503) via dependabot[bot] (@dependabot[bot])
