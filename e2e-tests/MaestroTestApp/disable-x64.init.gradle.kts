allprojects {
    afterEvaluate {
        // Disable all iosX64-related tasks to prevent x86_64 builds on arm64-only CI
        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }

        val buildDirPath = layout.buildDirectory.get().asFile.absolutePath

        // After podInstall generates the Pods project, patch build settings.
        // Runs AFTER pod install completes (doLast) so the Pods.xcodeproj exists.
        // This is a backup for the xcodebuild wrapper in project.pbxproj.
        tasks.matching {
            it.name.lowercase().contains("podinstall")
        }.configureEach {
            doLast {
                val cocoaDir = File(buildDirPath, "cocoapods")
                if (!cocoaDir.exists()) {
                    println("CI-init-doLast: cocoapods dir not found at ${cocoaDir.path}")
                    return@doLast
                }
                println("CI-init-doLast: scanning ${cocoaDir.path}")

                cocoaDir.walk()
                    .filter { it.isFile && (it.extension == "xcconfig" || it.name == "project.pbxproj") }
                    .forEach { file ->
                        val original = file.readText()
                        if (original.contains("ASSETCATALOG_COMPILER_APPICON_NAME")) {
                            val modified = original.replace(
                                Regex("""^.*ASSETCATALOG_COMPILER_APPICON_NAME.*\n?""", RegexOption.MULTILINE),
                                ""
                            )
                            file.writeText(modified)
                            println("CI-init-doLast: removed ASSETCATALOG_COMPILER_APPICON_NAME from ${file.path}")
                        }
                    }
            }
        }
    }
}
