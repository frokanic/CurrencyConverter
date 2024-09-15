plugins {
    alias(libs.plugins.currencyconverter.android.library)
    alias(libs.plugins.currencyconverter.android.hilt)
    alias(libs.plugins.currencyconverter.android.room)
}

android {
    namespace = "com.frokanic.cc.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:common"))
    implementation(project(":core:database"))

    implementation(libs.androidx.work)
    implementation(libs.hilt.ext.work)
}