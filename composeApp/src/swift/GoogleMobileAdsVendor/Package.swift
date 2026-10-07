// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "GoogleMobileAdsVendor",
    platforms: [.iOS(.v13)],
    products: [
        .library(name: "GoogleMobileAdsVendor", targets: ["GoogleMobileAdsVendor"])
    ],
    dependencies: [
        .package(url: "https://github.com/googleads/swift-package-manager-google-mobile-ads.git", from: "12.14.0")
    ],
    targets: [
        .target(
            name: "GoogleMobileAdsVendor",
            dependencies: [
                .product(name: "GoogleMobileAds", package: "swift-package-manager-google-mobile-ads")
            ]
        )
    ]
)
