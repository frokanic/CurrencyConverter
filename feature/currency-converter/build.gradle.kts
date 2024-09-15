plugins {
    alias(libs.plugins.currencyconverter.android.library)
    alias(libs.plugins.currencyconverter.android.feature)
    alias(libs.plugins.currencyconverter.android.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.frokanic.cc.currencyconverter"
}

dependencies {

    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
}