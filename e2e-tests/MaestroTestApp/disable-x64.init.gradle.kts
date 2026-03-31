// Disable all iosX64-related tasks to prevent x86_64 builds on arm64-only CI
allprojects {
    afterEvaluate {
        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }
    }
}
