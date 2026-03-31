allprojects {
    afterEvaluate {
        // Disable all iosX64-related tasks to prevent x86_64 builds on arm64-only CI
        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }

        val buildDirPath = layout.buildDirectory.get().asFile.absolutePath

        // Inject build settings into CocoaPods Podfiles via post_install hook
        tasks.matching {
            it.name.lowercase().contains("podinstall")
        }.configureEach {
            doFirst {
                val settingsCode = """
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['ENABLE_DEBUG_DYLIB'] = 'NO'
      config.build_settings['DEBUG_INFORMATION_FORMAT'] = 'dwarf'
      config.build_settings['ASSETCATALOG_COMPILER_GENERATE_ASSET_SYMBOLS'] = 'NO'
      config.build_settings.delete('ASSETCATALOG_COMPILER_APPICON_NAME')
      config.build_settings['ARCHS'] = 'arm64'
      config.build_settings['ONLY_ACTIVE_ARCH'] = 'YES'
    end
  end"""

                val cocoaDir = File(buildDirPath, "cocoapods")
                if (cocoaDir.exists()) {
                    cocoaDir.walk().filter { it.name == "Podfile" }.forEach { podfile ->
                        val content = podfile.readText()
                        if (content.contains("ENABLE_DEBUG_DYLIB")) return@forEach

                        if (content.contains("post_install do |installer|")) {
                            val modified = content.replace(
                                "post_install do |installer|",
                                "post_install do |installer|" + settingsCode
                            )
                            podfile.writeText(modified)
                        } else {
                            podfile.appendText(
                                "\npost_install do |installer|" + settingsCode + "\nend\n"
                            )
                        }
                    }
                }
            }
        }

        // Remove ASSETCATALOG_COMPILER_APPICON_NAME from ALL config files before xcodebuild.
        // Resource bundle targets inherit AppIcon but their xcassets don't contain one,
        // causing actool to fail with "None of the input catalogs contained a matching
        // app icon set named AppIcon". The setting can be in .xcconfig or project.pbxproj.
        tasks.matching {
            it.name.lowercase().contains("podbuild")
        }.configureEach {
            doFirst {
                val cocoaDir = File(buildDirPath, "cocoapods")
                if (cocoaDir.exists()) {
                    cocoaDir.walk()
                        .filter { it.isFile && (it.extension == "xcconfig" || it.name == "project.pbxproj") }
                        .forEach { file ->
                            val content = file.readText()
                            if (content.contains("ASSETCATALOG_COMPILER_APPICON_NAME")) {
                                val modified = content.replace(
                                    Regex("""^.*ASSETCATALOG_COMPILER_APPICON_NAME.*$""", RegexOption.MULTILINE),
                                    ""
                                )
                                file.writeText(modified)
                                println("Removed ASSETCATALOG_COMPILER_APPICON_NAME from ${file.path}")
                            }
                        }
                } else {
                    println("WARNING: cocoapods dir does not exist at $buildDirPath/cocoapods")
                }
            }
        }
    }
}
