// swift-tools-version: 5.9
import PackageDescription
let package = Package(
  name: "_internal_linkage_SwiftPMImport",
  platforms: [
    .iOS("15.0"),
  ],
  products: [
      .library(
          name: "_internal_linkage_SwiftPMImport",
          type: .none,
          targets: ["_internal_linkage_SwiftPMImport"]
      ),
  ],
  dependencies: [
    .package(path: "subpackages/_revenuecatui"),
    .package(path: "subpackages/_core"),
    .package(path: "subpackages/_mappings"),
    .package(path: "subpackages/_models"),
  ],
  targets: [
    .target(
      name: "_internal_linkage_SwiftPMImport",
      dependencies: [
        .product(name: "_revenuecatui", package: "_revenuecatui"),
        .product(name: "_core", package: "_core"),
        .product(name: "_mappings", package: "_mappings"),
        .product(name: "_models", package: "_models"),
      ]
    ),
  ]
)
