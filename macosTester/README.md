# macosApp

SwiftUI tester app for running the SDK on macOS.

The Kotlin side lives in the `:macosTester` module, which exports `MacosTester.framework`
(`core` + `models`).

## Running the sample

Set `revenuecat.apiKey.apple` in the root `local.properties`, same as `composeApp`.

Open `macosApp.xcodeproj` and run the `macosApp` scheme.

Test Store keys (`test_`) purchase through an `NSAlert` and work in an unsigned build. Real
App Store purchases need a signed, App-Sandboxed app, which this project is not set up for.

To build from commandline:

```sh
xcodebuild -project macosTester/macosApp/macosApp.xcodeproj -scheme macosApp \
  -configuration Debug -destination 'platform=macOS' CODE_SIGNING_ALLOWED=NO build
```
