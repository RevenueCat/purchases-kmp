println("CI-INIT-SCRIPT: loaded")

allprojects {
    afterEvaluate {
        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }
    }
}

gradle.taskGraph.whenReady {
    println("CI-INIT-SCRIPT: taskGraph ready, ${allTasks.size} tasks")
    allTasks.filter { it.name.lowercase().contains("podinstall") }.forEach { task ->
        println("CI-INIT-SCRIPT: adding doLast to ${task.path}")
        task.doLast {
            val cocoaDir = project.layout.buildDirectory.get().asFile.resolve("cocoapods")
            println("CI-PATCH: afterPodInstall ${task.path} cocoaDir=${cocoaDir} exists=${cocoaDir.exists()}")
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
            println("CI-PATCH: patched $patchCount files total for ${task.path}")
        }
    }
}
