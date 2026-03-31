// After podInstall generates the Pods project, patch build settings to fix
// Xcode 16.4+ debug dylib _main symbol issue and AppIcon errors.
gradle.taskGraph.afterTask {
    if (state.failure == null && name.lowercase().contains("podinstall")) {
        val cocoaDir = project.layout.buildDirectory.get().asFile.resolve("cocoapods")
        if (!cocoaDir.exists()) return@afterTask

        cocoaDir.walk()
            .filter { it.isFile && (it.extension == "xcconfig" || it.name == "project.pbxproj") }
            .forEach { file ->
                var text = file.readText()
                var modified = false

                if (text.contains("ASSETCATALOG_COMPILER_APPICON_NAME")) {
                    text = text.replace(
                        Regex("""^.*ASSETCATALOG_COMPILER_APPICON_NAME.*\n?""", RegexOption.MULTILINE), ""
                    )
                    modified = true
                }

                if (file.extension == "xcconfig" && !text.contains("ENABLE_DEBUG_DYLIB")) {
                    text += "\nENABLE_DEBUG_DYLIB = NO\n"
                    modified = true
                }

                if (modified) {
                    file.writeText(text)
                    println("CI-init: patched ${file.path}")
                }
            }
    }
}

allprojects {
    afterEvaluate {
        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }
    }
}
