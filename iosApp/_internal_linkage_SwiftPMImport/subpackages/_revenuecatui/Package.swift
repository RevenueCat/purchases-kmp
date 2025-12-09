// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_revenuecatui",
  platforms: [
    .iOS("15.0"),
  ],
  products: [
      .library(
          name: "_revenuecatui",
          type: .none,
          targets: ["_revenuecatui"]
      ),
  ],
  dependencies: [
    .package(
      url: "https://github.com/RevenueCat/purchases-hybrid-common.git",
      exact: "17.21.2",
    ),
  ],
  targets: [
    .target(
      name: "_revenuecatui",
      dependencies: [
        .product(
          name: "PurchasesHybridCommonUI",
          package: "purchases-hybrid-common",
        ),
      ]
    ),
  ]
)
