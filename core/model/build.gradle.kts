plugins {
    alias(libs.plugins.currencyconverter.jvm.library)
    id("kotlinx-serialization")
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}