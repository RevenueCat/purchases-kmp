allprojects {
    afterEvaluate {
        tasks.matching {
            it.name.lowercase().contains("iosx64")
        }.configureEach {
            enabled = false
        }
    }
}
