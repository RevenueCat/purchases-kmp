// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "AdditionalSwift",
    platforms: [
        .macOS(.v10_15),
        .watchOS("6.2"),
        .tvOS(.v13),
        .iOS(.v13),
        .visionOS(.v1)
    ],
    products: [
        .library(name: "AdditionalSwift", targets: ["AdditionalSwift"])
    ],
    targets: [
        .target(name: "AdditionalSwift")
    ]
)

