import Foundation
@_spi(Experimental) import RevenueCat

/// Bridges the SPI-gated `Configuration.Builder.with(useExternalPurchaseCustomLinks:)` into an `@objc`-visible entry
/// point, since Kotlin/Native cinterop only sees declarations reachable from the generated Objective-C header.
@objc
public class ExternalPurchaseCustomLinks: NSObject {

    /// `builder` is typed as `NSObject` because `RevenueCat`'s types are bound in a separate cinterop package, so
    /// Kotlin can't pass its `RCConfigurationBuilder` where this module's binding of it would be expected.
    @objc
    public static func configure(builder: NSObject, useExternalPurchaseCustomLinks: Bool) {
        guard let builder = builder as? Configuration.Builder else {
            assertionFailure("Expected a Configuration.Builder, got \(type(of: builder))")
            return
        }
        _ = builder.with(useExternalPurchaseCustomLinks: useExternalPurchaseCustomLinks)
    }
}
