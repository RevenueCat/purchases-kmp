import SwiftUI

@main
struct MaestroTestAppIOSApp: App {
    init() {
        NSSetUncaughtExceptionHandler { exception in
            NSLog("MaestroTestApp CRASH: %@", exception)
            NSLog("MaestroTestApp CRASH reason: %@", exception.reason ?? "nil")
            NSLog("MaestroTestApp CRASH stack: %@", exception.callStackSymbols.joined(separator: "\n"))
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
