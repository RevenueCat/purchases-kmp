allprojects {
    afterEvaluate {
        // Disable all iosX64-related tasks to prevent x86_64 builds on arm64-only CI
        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }

        val buildDirPath = layout.buildDirectory.get().asFile.absolutePath

        // After podInstall finishes (generates Podfile AND runs pod install),
        // patch the generated Pods project to fix Xcode 26.3 issues.
        // Using doLast ensures the Pods.xcodeproj already exists.
        tasks.matching {
            it.name.lowercase().contains("podinstall")
        }.configureEach {
            doLast {
                val cocoaDir = File(buildDirPath, "cocoapods")
                if (!cocoaDir.exists()) {
                    println("CI-init: cocoapods dir not found at ${cocoaDir.path}")
                    return@doLast
                }

                cocoaDir.walk()
                    .filter { it.isFile && (it.extension == "xcconfig" || it.name == "project.pbxproj") }
                    .forEach { file ->
                        val original = file.readText()
                        var modified = original

                        // Resource bundle targets inherit ASSETCATALOG_COMPILER_APPICON_NAME = AppIcon
                        // but have no AppIcon set, causing actool to fail. Remove it entirely.
                        modified = modified.replace(
                            Regex("""^.*ASSETCATALOG_COMPILER_APPICON_NAME.*\n?""", RegexOption.MULTILINE),
                            ""
                        )

                        // Disable Xcode 26.3 debug dylib feature (causes _main not found errors)
                        if (file.extension == "xcconfig" && !modified.contains("ENABLE_DEBUG_DYLIB")) {
                            modified += "\nENABLE_DEBUG_DYLIB = NO\n"
                            modified += "DEBUG_INFORMATION_FORMAT = dwarf\n"
                            modified += "ASSETCATALOG_COMPILER_GENERATE_ASSET_SYMBOLS = NO\n"
                            modified += "ONLY_ACTIVE_ARCH = YES\n"
                            modified += "ARCHS = arm64\n"
                        }

                        if (modified != original) {
                            file.writeText(modified)
                            println("CI-init: patched ${file.path}")
                        }
                    }
            }
        }
    }
}
