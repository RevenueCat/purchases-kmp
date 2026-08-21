# watchosApp

SwiftUI tester app for running the SDK on watchOS. Not published, not a sample: it exists to
exercise the SDK on a real watch runtime, which no simulator can do for 32-bit devices.

The Kotlin side lives in the `:watchosTester` module, which exports `WatchosTester.framework`
(`core` + `models`). A build phase runs `:watchosTester:embedAndSignAppleFrameworkForXcode`, so the
framework is rebuilt along with the app. `User Script Sandboxing` must stay disabled for that phase
to work; the project already sets it.

Set `revenuecat.apiKey.apple` in the root `local.properties`, same as `composeApp`. buildkonfig
bakes it into the framework, which the app reads via `WatchosTesterConfig`.

Open `watchosApp.xcodeproj` and run the `watchosApp` scheme. The framework is arm64 only, since
Kotlin 2.3.20 deprecated `watchosX64`, so the project excludes `x86_64` for the watch simulator:

```sh
xcodebuild -project watchosTester/watchosApp/watchosApp.xcodeproj -scheme watchosApp \
  -configuration Debug -destination 'generic/platform=watchOS Simulator' build
```
