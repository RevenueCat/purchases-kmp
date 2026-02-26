import SwiftUI

@main
struct iOSApp: App {
    var offeringId: String? {
        UserDefaults.standard.string(forKey: "offering_id")
    }

    var body: some Scene {
        WindowGroup {
            ContentView(offeringId: offeringId)
        }
    }
}