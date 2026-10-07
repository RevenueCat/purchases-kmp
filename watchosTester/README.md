# watchosApp

SwiftUI tester app for running the SDK on watchOS.

The Kotlin side lives in the `:watchosTester` module, which exports `WatchosTester.framework`
(`core` + `models`). 

## Running the sample

Set `revenuecat.apiKey.apple` in the root `local.properties`, same as `composeApp`.

Open `watchosApp.xcodeproj` and run the `watchosApp` scheme.

To build from commandline:

```sh
xcodebuild -project watchosTester/watchosApp/watchosApp.xcodeproj -scheme watchosApp \
  -configuration Debug -destination 'generic/platform=watchOS Simulator' build
```
