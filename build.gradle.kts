plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
    alias(libs.plugins.adamko.dokkatoo.html).apply(false)
}
