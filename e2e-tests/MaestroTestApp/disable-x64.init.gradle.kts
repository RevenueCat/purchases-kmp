allprojects {
    afterEvaluate {
        // Disable all iosX64-related tasks to prevent x86_64 builds on arm64-only CI
        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }

        // Inject ENABLE_DEBUG_DYLIB=NO into CocoaPods Podfiles to prevent Xcode 26.3
        // from creating debug dylibs for framework targets (which lack _main).
        val buildDirPath = layout.buildDirectory.get().asFile.absolutePath

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
    }
}
