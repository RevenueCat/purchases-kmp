import Foundation
@_spi(Experimental) import RevenueCat

/// Bridges the SPI-gated reward-verification API into `@objc`-visible types, since Kotlin/Native cinterop
/// only sees declarations reachable from the generated Objective-C header.
@objc
public class RewardVerification: NSObject {

    @objc
    public static func generateRewardVerificationToken(impressionId: String) -> RewardVerificationToken {
        let token = Purchases.shared.generateRewardVerificationToken(impressionId: impressionId)
        return RewardVerificationToken(
            customData: token.customData,
            clientTransactionId: token.clientTransactionID,
            appUserID: token.appUserID
        )
    }

    @objc
    public static func pollRewardVerification(
        clientTransactionId: String,
        completion: @escaping (RewardVerificationResult) -> Void
    ) {
        Task { @MainActor in
            let result = await Purchases.shared.pollRewardVerification(clientTransactionID: clientTransactionId)
            completion(RewardVerificationResult(result))
        }
    }
}

@objc
public class RewardVerificationToken: NSObject {
    @objc public let customData: String
    @objc public let clientTransactionId: String
    @objc public let appUserID: String

    init(customData: String, clientTransactionId: String, appUserID: String) {
        self.customData = customData
        self.clientTransactionId = clientTransactionId
        self.appUserID = appUserID
    }
}

@objc
public enum VerifiedRewardKind: Int {
    case virtualCurrency
    case entitlement
    case noReward
    case unsupportedReward
}

/// `@objc`-visible mirror of `AdReward`, whose own cases (virtual currency / entitlement / no reward /
/// unsupported) aren't distinguishable via a public raw value, so `kind` is derived here instead.
@objc
public class VerifiedReward: NSObject {
    @objc public let kind: VerifiedRewardKind
    @objc public let virtualCurrencyCode: String?
    @objc public let virtualCurrencyAmount: Int
    @objc public let entitlementIdentifier: String?
    @objc public let entitlementExpiresAtMillis: Int64

    private init(
        kind: VerifiedRewardKind,
        virtualCurrencyCode: String? = nil,
        virtualCurrencyAmount: Int = 0,
        entitlementIdentifier: String? = nil,
        entitlementExpiresAtMillis: Int64 = 0
    ) {
        self.kind = kind
        self.virtualCurrencyCode = virtualCurrencyCode
        self.virtualCurrencyAmount = virtualCurrencyAmount
        self.entitlementIdentifier = entitlementIdentifier
        self.entitlementExpiresAtMillis = entitlementExpiresAtMillis
    }

    convenience init(_ adReward: AdReward) {
        if let virtualCurrency = adReward.virtualCurrency {
            self.init(
                kind: .virtualCurrency,
                virtualCurrencyCode: virtualCurrency.code,
                virtualCurrencyAmount: virtualCurrency.amount
            )
        } else if let entitlement = adReward.entitlement {
            self.init(
                kind: .entitlement,
                entitlementIdentifier: entitlement.identifier,
                entitlementExpiresAtMillis: Int64(entitlement.expiresAt.timeIntervalSince1970 * 1000)
            )
        } else if adReward == .noReward {
            self.init(kind: .noReward)
        } else {
            self.init(kind: .unsupportedReward)
        }
    }
}

@objc
public class RewardVerificationResult: NSObject {
    @objc public let failed: Bool
    @objc public let verifiedReward: VerifiedReward?
    @objc public let moreRewards: [VerifiedReward]

    init(_ result: RevenueCat.RewardVerificationResult) {
        self.failed = result.verifiedReward == nil
        self.verifiedReward = result.verifiedReward.map(VerifiedReward.init)
        self.moreRewards = result.moreRewards.map(VerifiedReward.init)
    }
}
