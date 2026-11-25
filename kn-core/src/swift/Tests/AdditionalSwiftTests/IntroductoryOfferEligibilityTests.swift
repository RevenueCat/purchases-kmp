import Foundation
import StoreKit
import XCTest
@_spi(Internal) import RevenueCat
@testable import AdditionalSwift

final class IntroductoryOfferEligibilityTests: XCTestCase {
    func testRejectsObjectsThatAreNotStoreProducts() {
        XCTAssertFalse(AppleApiAvailability().isIntroductoryOfferEligibilityAvailableForProduct(NSObject()))
    }

    func testRejectsStoreKit1Products() {
        let product = SKProduct()
        product.setValue("test_product", forKey: "productIdentifier")
        XCTAssertFalse(AppleApiAvailability().isIntroductoryOfferEligibilityAvailableForProduct(
            StoreProduct(sk1Product: product)
        ))
    }

    func testRejectsTestStoreProducts() {
        let product = TestStoreProduct(localizedTitle: "Test", price: 1, currencyCode: "USD",
                                       localizedPriceString: "$1", productIdentifier: "test_product",
                                       productType: .autoRenewableSubscription, localizedDescription: "Test",
                                       locale: Locale(identifier: "en_US")).toStoreProduct()
        XCTAssertFalse(AppleApiAvailability().isIntroductoryOfferEligibilityAvailableForProduct(product))
    }
}
