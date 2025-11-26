// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_mappings",
  platforms: [
    .iOS("15.0"),
  ],
  products: [
      .library(
          name: "_mappings",
          type: .none,
          targets: ["_mappings"]
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
      name: "_mappings",
      dependencies: [
        .product(
          name: "PurchasesHybridCommon",
          package: "purchases-hybrid-common",
        ),
      ]
    ),
  ]
)
