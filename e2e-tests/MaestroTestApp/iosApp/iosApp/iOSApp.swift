import SwiftUI

@main
struct MaestroTestAppIOSApp: App {
    private var testFlow: String? {
        UserDefaults.standard.string(forKey: "e2e_test_flow")
    }

    init() {
        // Log crash details to NSLog so they appear in CI simulator logs
        NSSetUncaughtExceptionHandler { exception in
            let info = """
            CRASH: \(exception.name.rawValue)
            Reason: \(exception.reason ?? "unknown")
            Stack: \(exception.callStackSymbols.joined(separator: "\n"))
            """
            NSLog("MaestroTestApp CRASH: %@", info)
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView(testFlow: testFlow)
        }
    }
}
