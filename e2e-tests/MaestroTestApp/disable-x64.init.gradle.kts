// Gradle init script applied via --init-script on CI to work around build issues when building
// the MaestroTestApp for the iOS Simulator on Apple Silicon (arm64) CI runners.
//
// It applies three patches:
//
// 1. Disables all iosX64 Gradle tasks.
//    The CI macOS runners are Apple Silicon (arm64), so iosX64 targets are not needed.
//    Building them wastes time and can fail if the x64 simulator SDK is not installed.
//
// 2. Patches ASSETCATALOG_COMPILER_APPICON_NAME in CocoaPods-generated Xcode projects.
//    CocoaPods sets ASSETCATALOG_COMPILER_APPICON_NAME = "AppIcon" in the synthetic Xcode
//    project it generates for pod dependencies. When the pod target has no asset catalog
//    containing an "AppIcon" image set, Xcode fails with:
//      "None of the input catalogs contained a matching app icon set named 'AppIcon'."
//    This patches it to an empty string in both .pbxproj and .xcconfig files.
//    See: https://stackoverflow.com/questions/27966247
//
// 3. Adds ENABLE_DEBUG_DYLIB = NO to CocoaPods-generated .xcconfig files.
//    Xcode 16+ defaults ENABLE_DEBUG_DYLIB to YES for debug builds, which creates a
//    separate .debug.dylib alongside the main binary. This can cause "symbol not found"
//    errors at runtime when the CocoaPods framework is built as a static library but the
//    debug dylib references aren't resolved correctly.
//    See: https://developer.apple.com/documentation/xcode/understanding-build-product-layout-changes
//
// If CocoaPods or Xcode is upgraded, re-test without this script to check if the
// workarounds are still necessary.

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
