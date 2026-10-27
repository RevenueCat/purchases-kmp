// Created by Antonio Pallares on 3/10/26.
// Copyright © 2026 RevenueCat, Inc. All rights reserved.

import SwiftUI
import SDKUpdateTester

@main
struct HostApp: App {
    var body: some Scene {
        WindowGroup {
            ComposeView()
        }
    }
}

private struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            appUserIdToLogIn: UserDefaults.standard.string(forKey: "app_user_id_to_log_in")
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
