allprojects {
    afterEvaluate {
        // Disable all iosX64-related tasks to prevent x86_64 builds on arm64-only CI
        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }

        // Inject post_install hook into CocoaPods Podfiles to disable Xcode 26.3
        // debug dylib creation. The debug dylib requires a _main symbol that doesn't
        // exist in framework targets, causing linker failures.
        tasks.matching {
            it.name.lowercase().contains("podinstall")
        }.configureEach {
            doFirst {
                val cocoaDir = File(project.buildDir, "cocoapods")
                if (cocoaDir.exists()) {
                    cocoaDir.walk().filter { it.name == "Podfile" }.forEach { podfile ->
                        val content = podfile.readText()
                        if (!content.contains("ENABLE_DEBUG_DYLIB")) {
                            podfile.appendText("""

post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['ENABLE_DEBUG_DYLIB'] = 'NO'
      config.build_settings['DEBUG_INFORMATION_FORMAT'] = 'dwarf'
    end
  end
end
""")
                        }
                    }
                }
            }
        }
    }
}
