import SwiftUI
import MacosTester

@main
struct MacosTesterApp: App {
    init() {
        let apiKey = MacosTesterConfig.shared.apiKey
        Purchases.companion.logLevel = .debug
        _ = Purchases.companion.configure(
            configuration: PurchasesConfiguration.Builder(apiKey: apiKey).build()
        )
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
        .defaultSize(width: 560, height: 620)
    }
}
