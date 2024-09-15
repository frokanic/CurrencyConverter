plugins {
    alias(libs.plugins.currencyconverter.android.library)
    alias(libs.plugins.currencyconverter.android.hilt)
    alias(libs.plugins.currencyconverter.android.room)
}

android {
    namespace = "com.frokanic.cc.database"
}

dependencies {
    implementation(project(":core:model"))
}