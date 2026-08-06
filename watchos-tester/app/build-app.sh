#!/bin/bash
# Builds WatchTester.app for the watchOS simulator without an Xcode project.
# Pass --swift-only to skip the Gradle framework build when only Swift files changed.
set -euo pipefail

cd "$(dirname "$0")/../.."
FRAMEWORK_DIR="watchos-tester/build/bin/watchosSimulatorArm64/debugFramework"
APP_DIR="watchos-tester/app/build/WatchTester.app"
SDK="$(xcrun --sdk watchsimulator --show-sdk-path)"

if [[ "${1:-}" != "--swift-only" ]]; then
    ./gradlew :watchos-tester:linkDebugFrameworkWatchosSimulatorArm64
fi

rm -rf "$APP_DIR"
mkdir -p "$APP_DIR"

xcrun swiftc \
    -sdk "$SDK" \
    -target arm64-apple-watchos9.0-simulator \
    -F "$FRAMEWORK_DIR" \
    -framework WatchTester \
    -parse-as-library \
    watchos-tester/app/WatchTesterApp.swift \
    watchos-tester/app/ContentView.swift \
    -o "$APP_DIR/WatchTesterApp"

cp watchos-tester/app/Info.plist "$APP_DIR/Info.plist"
codesign --force --sign - "$APP_DIR"

echo "Built $APP_DIR (see watchos-tester/README.md to install and launch)"
