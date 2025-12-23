import Foundation

/// Provides runtime availability checks for Apple platform APIs.
@objc
public class AppleApiAvailability: NSObject {
    /// Determines if the Win-Back Offer APIs are available on the current device.
    ///
    /// Note: This only checks if the APIs are available in the current OS version,
    /// not if the SDK is using StoreKit 2, which is required for the APIs.
    ///
    /// - Returns: `true` if the Win-Back Offer APIs are available, `false` otherwise.
    @objc
    public func isWinBackOfferAPIAvailable() -> Bool {
        if #available(iOS 18.0, macOS 15.0, tvOS 18.0, watchOS 11.0, visionOS 2.0, *) {
            return true
        } else {
            return false
        }
    }

    /// Determines if the enableAdServicesAttributionTokenCollection API is available on the current device.
    ///
    /// - Returns: `true` if the ``CommonFunctionality/enableAdServicesAttributionTokenCollection()`` API is available,
    /// `false` otherwise.
    @objc
    public func isEnableAdServicesAttributionTokenCollectionAPIAvailable() -> Bool {
        #if os(tvOS) || os(watchOS)
        return false
        #else
        if #available(iOS 14.3, macOS 11.1, macCatalyst 14.3, *) {
            return true
        } else {
            return false
        }
        #endif
    }
}
