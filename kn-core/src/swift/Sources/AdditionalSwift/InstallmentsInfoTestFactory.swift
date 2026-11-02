import Foundation
import RevenueCat

/// Builds an `InstallmentsInfo` for tests. Its initializer takes `Decimal` prices, which Swift bridges
/// to Objective-C as the `NSDecimal` struct, which Kotlin/Native cinterop can't pass by value.
///
/// `billingPlanType` is typed as `NSObject` and the result as `NSObject?` to keep RevenueCat types out of
/// the generated Objective-C header, which cinterop can't resolve.
@objc
public class InstallmentsInfoTestFactory: NSObject {

    /// Returns an `RCInstallmentsInfo`, or `nil` if a period unit is invalid or `billingPlanType` is not an
    /// `RCBillingPlanType`.
    @objc
    // swiftlint:disable:next function_parameter_count
    public static func installmentsInfo(
        commitmentInstallmentsCount: Int,
        commitmentInstallmentPeriodValue: Int,
        commitmentInstallmentPeriodUnit: Int,
        installmentBillingPrice: NSDecimalNumber,
        installmentBillingDisplayPrice: String,
        commitmentTotalPeriodValue: Int,
        commitmentTotalPeriodUnit: Int,
        commitmentTotalPrice: NSDecimalNumber,
        commitmentTotalDisplayPrice: String,
        billingPlanType: NSObject
    ) -> NSObject? {
        guard let installmentPeriodUnit = SubscriptionPeriod.Unit(rawValue: commitmentInstallmentPeriodUnit),
              let totalPeriodUnit = SubscriptionPeriod.Unit(rawValue: commitmentTotalPeriodUnit),
              let billingPlanType = billingPlanType as? BillingPlanType else { return nil }

        return InstallmentsInfo(
            commitmentInstallmentsCount: commitmentInstallmentsCount,
            commitmentInstallmentPeriod: SubscriptionPeriod(
                value: commitmentInstallmentPeriodValue,
                unit: installmentPeriodUnit
            ),
            installmentBillingPrice: installmentBillingPrice as Decimal,
            installmentBillingDisplayPrice: installmentBillingDisplayPrice,
            commitmentTotalPeriod: SubscriptionPeriod(value: commitmentTotalPeriodValue, unit: totalPeriodUnit),
            commitmentTotalPrice: commitmentTotalPrice as Decimal,
            commitmentTotalDisplayPrice: commitmentTotalDisplayPrice,
            billingPlanType: billingPlanType
        )
    }
}
