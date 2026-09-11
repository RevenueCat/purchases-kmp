import Foundation
import RevenueCat

/// Bridges `Purchases.shared.adTracker` into `@objc`-visible entry points, since Kotlin/Native cinterop only
/// sees declarations reachable from the generated Objective-C header and the ad tracking API is Swift-only.
@available(iOS 15.0, tvOS 15.0, macOS 12.0, watchOS 8.0, *)
@objc
public class AdTracking: NSObject {

    @objc
    public static func trackAdDisplayed(
        networkName: String?,
        mediatorName: String,
        adFormat: String,
        placement: String?,
        adUnitId: String,
        impressionId: String
    ) {
        Purchases.shared.adTracker.trackAdDisplayed(.init(
            networkName: networkName,
            mediatorName: MediatorName(rawValue: mediatorName),
            adFormat: AdFormat(rawValue: adFormat),
            placement: placement,
            adUnitId: adUnitId,
            impressionId: impressionId
        ))
    }

    @objc
    public static func trackAdOpened(
        networkName: String?,
        mediatorName: String,
        adFormat: String,
        placement: String?,
        adUnitId: String,
        impressionId: String
    ) {
        Purchases.shared.adTracker.trackAdOpened(.init(
            networkName: networkName,
            mediatorName: MediatorName(rawValue: mediatorName),
            adFormat: AdFormat(rawValue: adFormat),
            placement: placement,
            adUnitId: adUnitId,
            impressionId: impressionId
        ))
    }

    @objc
    public static func trackAdLoaded(
        networkName: String?,
        mediatorName: String,
        adFormat: String,
        placement: String?,
        adUnitId: String,
        impressionId: String
    ) {
        Purchases.shared.adTracker.trackAdLoaded(.init(
            networkName: networkName,
            mediatorName: MediatorName(rawValue: mediatorName),
            adFormat: AdFormat(rawValue: adFormat),
            placement: placement,
            adUnitId: adUnitId,
            impressionId: impressionId
        ))
    }

    @objc
    public static func trackAdRevenue(
        networkName: String?,
        mediatorName: String,
        adFormat: String,
        placement: String?,
        adUnitId: String,
        impressionId: String,
        revenueMicros: Int64,
        currency: String,
        precision: String
    ) {
        Purchases.shared.adTracker.trackAdRevenue(.init(
            networkName: networkName,
            mediatorName: MediatorName(rawValue: mediatorName),
            adFormat: AdFormat(rawValue: adFormat),
            placement: placement,
            adUnitId: adUnitId,
            impressionId: impressionId,
            revenueMicros: Int(clamping: revenueMicros),
            currency: currency,
            precision: AdRevenue.Precision(rawValue: precision)
        ))
    }

    @objc
    public static func trackAdFailedToLoad(
        mediatorName: String,
        adFormat: String,
        placement: String?,
        adUnitId: String,
        mediatorErrorCode: NSNumber?
    ) {
        Purchases.shared.adTracker.trackAdFailedToLoad(.init(
            mediatorName: MediatorName(rawValue: mediatorName),
            adFormat: AdFormat(rawValue: adFormat),
            placement: placement,
            adUnitId: adUnitId,
            mediatorErrorCode: mediatorErrorCode?.intValue
        ))
    }
}
