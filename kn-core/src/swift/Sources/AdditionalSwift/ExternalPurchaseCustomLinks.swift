import Foundation
@_spi(Experimental) import RevenueCat

/// Bridges the SPI-gated
/// `Configuration.Builder.with(useExternalPurchaseCustomLinks:enableExternalPurchasesInSimulator:)` into an
/// `@objc`-visible entry point, since Kotlin/Native cinterop only sees declarations reachable from the generated
/// Objective-C header.
@objc
public class ExternalPurchaseCustomLinks: NSObject {

    @objc
    public static func configure(
        builder: Configuration.Builder,
        useExternalPurchaseCustomLinks: Bool,
        enableExternalPurchasesInSimulator: Bool
    ) {
        _ = builder.with(
            useExternalPurchaseCustomLinks: useExternalPurchaseCustomLinks,
            enableExternalPurchasesInSimulator: enableExternalPurchasesInSimulator
        )
    }
}
