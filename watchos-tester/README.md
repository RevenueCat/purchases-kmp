# watchos-tester

SwiftUI tester app to run the SDK on a watchOS simulator. Not published.

Built without an Xcode project: a static `WatchTester.framework` exporting `core` and `models`,
compiled and bundled by `app/build-app.sh` with raw `swiftc`. Lists the current offering's
packages and supports purchase, restore and customer info.

The API key is read from the `RC_API_KEY` environment variable at runtime (pass it with the
`SIMCTL_CHILD_` prefix so `simctl` forwards it). Never hardcode keys here.

```sh
./watchos-tester/app/build-app.sh
xcrun simctl install <watch-udid> watchos-tester/app/build/WatchTester.app
SIMCTL_CHILD_RC_API_KEY=<key> xcrun simctl launch <watch-udid> com.revenuecat.kmp.watchtester
```
