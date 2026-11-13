import Foundation
import RevenueCat

/// Bridges native configured rewards into Objective-C-visible values for Kotlin/Native.
@objc
public class AdRewardConfiguration: NSObject {
    @objc public let kind: ConfiguredRewardKind
    @objc public let virtualCurrencyCode: String?
    @objc public let virtualCurrencyAmount: Int64
    @objc public let entitlementIdentifier: String?
    @objc public let entitlementDurationSeconds: TimeInterval

    @objc
    public init(
        kind: ConfiguredRewardKind,
        virtualCurrencyCode: String?,
        virtualCurrencyAmount: Int64,
        entitlementIdentifier: String?,
        entitlementDurationSeconds: TimeInterval
    ) {
        self.kind = kind
        self.virtualCurrencyCode = virtualCurrencyCode
        self.virtualCurrencyAmount = virtualCurrencyAmount
        self.entitlementIdentifier = entitlementIdentifier
        self.entitlementDurationSeconds = entitlementDurationSeconds
    }

    convenience init(_ reward: RevenueCat.AdRewardConfiguration) {
        if let currency = reward.virtualCurrency {
            self.init(kind: .virtualCurrency, virtualCurrencyCode: currency.code,
                      virtualCurrencyAmount: Int64(currency.amount), entitlementIdentifier: nil,
                      entitlementDurationSeconds: 0)
        } else if let entitlement = reward.entitlement {
            self.init(kind: .entitlement, virtualCurrencyCode: nil, virtualCurrencyAmount: 0,
                      entitlementIdentifier: entitlement.identifier,
                      entitlementDurationSeconds: entitlement.duration)
        } else {
            self.init(kind: .unsupportedReward, virtualCurrencyCode: nil, virtualCurrencyAmount: 0,
                      entitlementIdentifier: nil, entitlementDurationSeconds: 0)
        }
    }
}

@objc
public enum ConfiguredRewardKind: Int {
    case virtualCurrency
    case entitlement
    case unsupportedReward
}

@objc
public class ConfiguredAdRewards: NSObject {
    @objc public let reward: AdRewardConfiguration?
    @objc public let moreRewards: [AdRewardConfiguration]

    @objc
    public init(reward: AdRewardConfiguration?, moreRewards: [AdRewardConfiguration]) {
        self.reward = reward
        self.moreRewards = moreRewards
    }

    convenience init(_ rewards: RevenueCat.ConfiguredAdRewards) {
        self.init(reward: rewards.reward.map(AdRewardConfiguration.init),
                  moreRewards: rewards.moreRewards.map(AdRewardConfiguration.init))
    }
}

@objc
public class AdRewardConfigurationProvider: NSObject {
    /// The native SDK owns cache, fetch coalescing, validation and exact ad-unit matching.
    @objc
    public static func getAdRewards(adUnitId: String, completion: @escaping (ConfiguredAdRewards?) -> Void) {
        Task { @MainActor in
            guard #available(iOS 15.0, tvOS 15.0, macOS 12.0, watchOS 8.0, *) else {
                completion(nil)
                return
            }
            let rewards = await Purchases.shared.adRewards(forAdUnitId: adUnitId)
            completion(rewards.map(ConfiguredAdRewards.init))
        }
    }
}
