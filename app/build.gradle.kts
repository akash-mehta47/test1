plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.micemanagement"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.micemanagement"
        minSdk = 27
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures{
        viewBinding =  true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    implementation("com.github.bumptech.glide:glide:4.15.1")

    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("com.airbnb.android:lottie:6.1.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.github.f0ris.sweetalert:library:1.6.2")

    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("com.journeyapps:zxing-android-embedded:4.3.0") { isTransitive = false }
    implementation("com.google.zxing:core:3.4.1")


}