import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    var offeringId: String?

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(offeringId: offeringId)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var offeringId: String?

    var body: some View {
        ComposeView(offeringId: offeringId)
            .ignoresSafeArea()
    }
}

