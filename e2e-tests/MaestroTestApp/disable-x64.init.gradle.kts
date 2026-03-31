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
                .filter { it.isFile && it.name == "project.pbxproj" }
                .forEach { file ->
                    var text = file.readText()
                    val hadSetting = text.contains("ASSETCATALOG_COMPILER_APPICON_NAME")
                    text = text.replace(Regex(".*ASSETCATALOG_COMPILER_APPICON_NAME.*\\n"), "")
                    text = text.replace(
                        "buildSettings = {",
                        "buildSettings = {\n\t\t\t\tASSETCATALOG_COMPILER_APPICON_NAME = \"\";"
                    )
                    file.writeText(text)
                    patchCount++
                    println("CI-PATCH: patched ${file.path} (had existing=${hadSetting})")
                }

            cocoaDir.walk()
                .filter { it.isFile && it.extension == "xcconfig" }
                .forEach { file ->
                    var text = file.readText()
                    var modified = false
                    text = text.replace(Regex("ASSETCATALOG_COMPILER_APPICON_NAME = .*"), "ASSETCATALOG_COMPILER_APPICON_NAME = ")
                    if (!text.contains("ASSETCATALOG_COMPILER_APPICON_NAME")) {
                        text += "\nASSETCATALOG_COMPILER_APPICON_NAME = \n"
                        modified = true
                    }
                    if (!text.contains("ENABLE_DEBUG_DYLIB")) {
                        text += "\nENABLE_DEBUG_DYLIB = NO\n"
                        modified = true
                    }
                    file.writeText(text)
                    patchCount++
                    if (modified) println("CI-PATCH: patched xcconfig ${file.path}")
                }
            println("CI-PATCH: patched $patchCount files total for ${task.path}")
        }
    }
}
