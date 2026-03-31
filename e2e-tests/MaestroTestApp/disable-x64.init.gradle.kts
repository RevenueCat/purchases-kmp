allprojects {
    afterEvaluate {
        val buildDir = layout.buildDirectory.get().asFile

        tasks.matching {
            it.name.lowercase().contains("podinstall")
        }.configureEach {
            doLast {
                val cocoaDir = buildDir.resolve("cocoapods")
                println("CI-init: afterPodInstall for ${this.name}, cocoaDir=$cocoaDir exists=${cocoaDir.exists()}")
                if (!cocoaDir.exists()) return@doLast

                cocoaDir.walk()
                    .filter { it.isFile && (it.extension == "xcconfig" || it.name == "project.pbxproj") }
                    .forEach { file ->
                        var text = file.readText()
                        var modified = false

                        if (text.contains("ASSETCATALOG_COMPILER_APPICON_NAME")) {
                            text = text.replace(
                                Regex("""^[^\n]*ASSETCATALOG_COMPILER_APPICON_NAME[^\n]*\n?""", RegexOption.MULTILINE), ""
                            )
                            modified = true
                            println("CI-init: removed ASSETCATALOG_COMPILER_APPICON_NAME from ${file.path}")
                        }

                        if (file.extension == "xcconfig" && !text.contains("ENABLE_DEBUG_DYLIB")) {
                            text += "\nENABLE_DEBUG_DYLIB = NO\n"
                            modified = true
                            println("CI-init: added ENABLE_DEBUG_DYLIB=NO to ${file.path}")
                        }

                        if (modified) {
                            file.writeText(text)
                        }
                    }
            }
        }

        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }
    }
}
