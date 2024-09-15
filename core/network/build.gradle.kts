plugins {
    alias(libs.plugins.currencyconverter.android.library)
    alias(libs.plugins.currencyconverter.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.frokanic.cc.network"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.retrofit.logger)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:model"))
}