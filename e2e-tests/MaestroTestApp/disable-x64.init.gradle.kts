allprojects {
    afterEvaluate {
        val buildDir = layout.buildDirectory.get().asFile

        tasks.matching {
            it.name.lowercase().contains("podinstall")
        }.configureEach {
            doLast {
                val cocoaDir = buildDir.resolve("cocoapods")
                println("CI-PATCH: afterPodInstall task=${name} cocoaDir=${cocoaDir} exists=${cocoaDir.exists()}")
                if (!cocoaDir.exists()) return@doLast

                var patchCount = 0
                cocoaDir.walk()
                    .filter { it.isFile && (it.extension == "xcconfig" || it.name == "project.pbxproj") }
                    .forEach { file ->
                        var text = file.readText()
                        var modified = false

                        if (text.contains("ASSETCATALOG_COMPILER_APPICON_NAME")) {
                            text = text.lines()
                                .filter { !it.contains("ASSETCATALOG_COMPILER_APPICON_NAME") }
                                .joinToString("\n")
                            modified = true
                            println("CI-PATCH: removed ASSETCATALOG_COMPILER_APPICON_NAME from ${file.path}")
                        }

                        if (file.extension == "xcconfig" && !text.contains("ENABLE_DEBUG_DYLIB")) {
                            text += "\nENABLE_DEBUG_DYLIB = NO\n"
                            modified = true
                        }

                        if (modified) {
                            file.writeText(text)
                            patchCount++
                        }
                    }
                println("CI-PATCH: patched $patchCount files total")
            }
        }

        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }
    }
}
