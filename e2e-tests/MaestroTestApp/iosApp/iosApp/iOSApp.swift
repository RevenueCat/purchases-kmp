import SwiftUI

@main
struct MaestroTestAppIOSApp: App {
    private var testFlow: String? {
        UserDefaults.standard.string(forKey: "e2e_test_flow")
    }

    var body: some Scene {
        WindowGroup {
            ContentView(testFlow: testFlow)
        }
    }
}
