import Foundation
@_spi(Internal) import RevenueCat

/// Bridges the SPI-gated `StoreProduct.id` into an `@objc`-visible API, since Kotlin/Native cinterop
/// only sees declarations reachable from the generated Objective-C header.
///
/// Parameters are typed as `NSObject` to keep RevenueCat types out of the generated Objective-C header,
/// which cinterop can't resolve.
@objc
public class StoreProductIdentifier: NSObject {

    /// Returns `id`, which includes the product's billing plan (e.g. "annual:monthly"), or `nil` if
    /// `storeProduct` is not an `RCStoreProduct`.
    @objc
    public static func identifier(_ storeProduct: NSObject) -> String? {
        guard let storeProduct = storeProduct as? StoreProduct else { return nil }
        return storeProduct.id
    }
}
