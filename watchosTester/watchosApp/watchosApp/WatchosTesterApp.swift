import SwiftUI
import WatchosTester

@main
struct WatchosTesterApp: App {
    init() {
        let apiKey = WatchosTesterConfig.shared.apiKey
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
