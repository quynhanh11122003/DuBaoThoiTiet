plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.dubaott"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dubaott"
        minSdk = 24
        targetSdk = 34
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.android.volley:volley:1.2.0")
    implementation("com.squareup.picasso:picasso:2.8")
    // implementation ("com.example:library_name:1.0.0")
    implementation ("androidx.core:core-ktx:1.6.0")
    implementation ("androidx.core:core:1.6.0'")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation ("com.github.TutorialsAndroid:GButton:v1.0.19")
    implementation ("com.google.android.gms:play-services-auth:20.4.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.google.android.gms:play-services-location:18.0.0")
    implementation ("me.relex:circleindicator:2.1.6")
    implementation ("androidx.viewpager:viewpager:1.0.0")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")


}