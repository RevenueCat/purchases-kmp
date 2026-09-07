import SwiftUI
import MacosTester

private enum Status {
    case busy(String)
    case ok(String)
    case failed(String)

    var style: (text: String, symbol: String, tint: Color) {
        switch self {
        case .busy(let message): return (message, "arrow.triangle.2.circlepath", .accentColor)
        case .ok(let message): return (message, "checkmark.circle.fill", .green)
        case .failed(let message): return (message, "exclamationmark.triangle.fill", .red)
        }
    }

    var isBusy: Bool {
        if case .busy = self { return true }
        return false
    }
}

struct ContentView: View {
    @State private var status: Status = .busy("Loading offerings...")
    @State private var packages: [any Package] = []

    private var purchases: Purchases { Purchases.companion.sharedInstance }

    var body: some View {
        Form {
            Section("Status") {
                statusRow(status.style)
            }

            Section {
                HStack(spacing: 8) {
                    Button { fetchOfferings() } label: {
                        Label("Reload offerings", systemImage: "arrow.clockwise")
                            .frame(maxWidth: .infinity)
                    }
                    Button { restore() } label: {
                        Label("Restore", systemImage: "arrow.counterclockwise.circle")
                            .frame(maxWidth: .infinity)
                    }
                    Button { fetchCustomerInfo() } label: {
                        Label("Customer info", systemImage: "person.crop.circle")
                            .frame(maxWidth: .infinity)
                    }
                }
                .buttonStyle(.bordered)
            }

            Section("Packages") {
                if packages.isEmpty {
                    Text("No packages in the current offering.")
                        .foregroundStyle(.secondary)
                } else {
                    ForEach(packages, id: \.identifier) { pkg in
                        packageRow(pkg)
                    }
                }
            }
        }
        .formStyle(.grouped)
        .disabled(status.isBusy)
        .onAppear { fetchOfferings() }
    }

    private func statusRow(_ style: (text: String, symbol: String, tint: Color)) -> some View {
        HStack(alignment: .firstTextBaseline, spacing: 8) {
            Image(systemName: style.symbol)
                .foregroundStyle(style.tint)
                .symbolRenderingMode(.hierarchical)
            Text(style.text)
                .textSelection(.enabled)
                .fixedSize(horizontal: false, vertical: true)
            Spacer()
            if status.isBusy {
                ProgressView().controlSize(.small)
            }
        }
    }

    private func packageRow(_ pkg: any Package) -> some View {
        HStack(spacing: 12) {
            VStack(alignment: .leading, spacing: 2) {
                Text(pkg.identifier)
                    .font(.headline)
                Text(pkg.storeProduct.id)
                    .font(.caption)
                    .monospaced()
                    .foregroundStyle(.secondary)
                    .textSelection(.enabled)
            }
            Spacer()
            Text(pkg.storeProduct.price.formatted)
                .font(.body.weight(.medium))
                .monospacedDigit()
            Button("Buy") { purchase(pkg) }
                .buttonStyle(.borderedProminent)
        }
        .padding(.vertical, 2)
    }

    private func fetchOfferings() {
        status = .busy("Loading offerings...")
        purchases.getOfferings(
            onError: { error in
                status = .failed("Offerings error: \(error.code) \(error.underlyingErrorMessage ?? "")")
            },
            onSuccess: { offerings in
                packages = offerings.current?.availablePackages ?? []
                status = .ok("\(offerings.all.count) offerings, current=\(offerings.current?.identifier ?? "none")")
            }
        )
    }

    private func purchase(_ pkg: any Package) {
        status = .busy("Purchasing \(pkg.identifier)...")
        purchases.purchase(
            packageToPurchase: pkg,
            onError: { error, userCancelled in
                status = .failed("Purchase error: \(error.code) cancelled=\(userCancelled)")
            },
            onSuccess: { transaction, customerInfo in
                status = .ok("Purchased! tx=\(transaction.transactionId ?? "?") "
                    + "entitlements=\(customerInfo.entitlements.active.keys)")
            },
            isPersonalizedPrice: nil,
            oldProductId: nil,
            replacementMode: nil
        )
    }

    private func restore() {
        status = .busy("Restoring...")
        purchases.restorePurchases(
            onError: { error in status = .failed("Restore error: \(error.code)") },
            onSuccess: { customerInfo in
                status = .ok("Restored. Active subs: \(customerInfo.activeSubscriptions)")
            }
        )
    }

    private func fetchCustomerInfo() {
        status = .busy("Fetching customer info...")
        purchases.getCustomerInfo(
            fetchPolicy: .cachedOrFetched,
            onError: { error in status = .failed("CustomerInfo error: \(error.code)") },
            onSuccess: { customerInfo in
                status = .ok("user=\(customerInfo.originalAppUserId), "
                    + "active subs: \(customerInfo.activeSubscriptions.count), "
                    + "entitlements: \(customerInfo.entitlements.active.count)")
            }
        )
    }
}
