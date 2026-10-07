import SwiftUI
import WatchosTester

struct ContentView: View {
    @State private var status = ""
    @State private var packages: [any Package] = []

    private var purchases: Purchases { Purchases.companion.sharedInstance }

    var body: some View {
        List {
            Section("Status") {
                Text(status).font(.footnote)
            }
            Section("Packages") {
                ForEach(packages.indices, id: \.self) { index in
                    let pkg = packages[index]
                    Button {
                        purchase(pkg)
                    } label: {
                        VStack(alignment: .leading) {
                            Text(pkg.identifier).font(.caption)
                            Text(pkg.storeProduct.price.formatted)
                                .font(.caption2)
                                .foregroundStyle(.secondary)
                        }
                    }
                }
            }
            Section {
                Button("Restore") { restore() }
                Button("Customer info") { fetchCustomerInfo() }
                Button("Reload offerings") { fetchOfferings() }
            }
        }
        .onAppear { fetchOfferings() }
    }

    private func fetchOfferings() {
        status = "Loading offerings..."
        purchases.getOfferings(
            onError: { error in
                status = "Offerings error: \(error.code) \(error.underlyingErrorMessage ?? "")"
            },
            onSuccess: { offerings in
                packages = offerings.current?.availablePackages ?? []
                status = "\(offerings.all.count) offerings, current=\(offerings.current?.identifier ?? "none")"
            }
        )
    }

    private func purchase(_ pkg: any Package) {
        status = "Purchasing \(pkg.identifier)..."
        purchases.purchase(
            packageToPurchase: pkg,
            onError: { error, userCancelled in
                status = "Purchase error: \(error.code) cancelled=\(userCancelled)"
            },
            onSuccess: { transaction, customerInfo in
                status = "Purchased! tx=\(transaction.transactionId ?? "?") "
                    + "entitlements=\(customerInfo.entitlements.active.keys)"
            },
            isPersonalizedPrice: nil,
            oldProductId: nil,
            replacementMode: nil
        )
    }

    private func restore() {
        status = "Restoring..."
        purchases.restorePurchases(
            onError: { error in status = "Restore error: \(error.code)" },
            onSuccess: { customerInfo in
                status = "Restored. Active subs: \(customerInfo.activeSubscriptions)"
            }
        )
    }

    private func fetchCustomerInfo() {
        status = "Fetching customer info..."
        purchases.getCustomerInfo(
            fetchPolicy: .cachedOrFetched,
            onError: { error in status = "CustomerInfo error: \(error.code)" },
            onSuccess: { customerInfo in
                status = "user=\(customerInfo.originalAppUserId), "
                    + "active subs: \(customerInfo.activeSubscriptions.count), "
                    + "entitlements: \(customerInfo.entitlements.active.count)"
            }
        )
    }
}
