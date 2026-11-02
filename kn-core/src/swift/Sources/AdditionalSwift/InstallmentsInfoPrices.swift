import Foundation
import RevenueCat

/// Exposes `InstallmentsInfo`'s `Decimal` prices as `NSDecimalNumber`. Swift's `Decimal` is bridged to
/// Objective-C as the `NSDecimal` struct, which Kotlin/Native cinterop can't return by value.
///
/// Parameters are typed as `NSObject` to keep RevenueCat types out of the generated Objective-C header,
/// which cinterop can't resolve.
@objc
public class InstallmentsInfoPrices: NSObject {

    /// Returns `installmentBillingPrice` as an `NSDecimalNumber`, or `nil` if `installmentsInfo` is not an
    /// `RCInstallmentsInfo`.
    @objc
    public static func installmentBillingPrice(_ installmentsInfo: NSObject) -> NSDecimalNumber? {
        guard let installmentsInfo = installmentsInfo as? InstallmentsInfo else { return nil }
        return installmentsInfo.installmentBillingPrice as NSDecimalNumber
    }

    /// Returns `commitmentTotalPrice` as an `NSDecimalNumber`, or `nil` if `installmentsInfo` is not an
    /// `RCInstallmentsInfo`.
    @objc
    public static func commitmentTotalPrice(_ installmentsInfo: NSObject) -> NSDecimalNumber? {
        guard let installmentsInfo = installmentsInfo as? InstallmentsInfo else { return nil }
        return installmentsInfo.commitmentTotalPrice as NSDecimalNumber
    }
}
