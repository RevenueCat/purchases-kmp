import SwiftUI
import WatchTester

@main
struct WatchTesterApp: App {
    init() {
        let apiKey = ProcessInfo.processInfo.environment["RC_API_KEY"] ?? "appl_SmokeTestInvalidKey"
        Purchases.companion.logLevel = .debug
        _ = Purchases.companion.configure(
            configuration: PurchasesConfiguration.Builder(apiKey: apiKey).build()
        )
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
