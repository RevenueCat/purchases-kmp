// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_core",
  platforms: [
    .iOS("15.0"),
  ],
  products: [
      .library(
          name: "_core",
          type: .none,
          targets: ["_core"]
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
      name: "_core",
      dependencies: [
        .product(
          name: "PurchasesHybridCommon",
          package: "purchases-hybrid-common",
        ),
      ]
    ),
  ]
)
