import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    var testFlow: String?

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(testFlow: testFlow)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var testFlow: String?

    var body: some View {
        ComposeView(testFlow: testFlow)
            .ignoresSafeArea(.keyboard)
    }
}
