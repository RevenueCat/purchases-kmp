// Registered as a taskGraph listener so it fires between tasks,
// after podInstall completes but before podBuild starts.
gradle.taskGraph.afterTask {
    if (state.failure == null && name.lowercase().contains("podinstall")) {
        val cocoaDir = project.layout.buildDirectory.get().asFile.resolve("cocoapods")
        if (!cocoaDir.exists()) return@afterTask

        cocoaDir.walk()
            .filter { it.isFile && (it.extension == "xcconfig" || it.name == "project.pbxproj") }
            .forEach { file ->
                val original = file.readText()
                if (original.contains("ASSETCATALOG_COMPILER_APPICON_NAME")) {
                    file.writeText(
                        original.replace(
                            Regex("""^.*ASSETCATALOG_COMPILER_APPICON_NAME.*\n?""", RegexOption.MULTILINE),
                            ""
                        )
                    )
                    println("CI-init: removed ASSETCATALOG_COMPILER_APPICON_NAME from ${file.path}")
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
