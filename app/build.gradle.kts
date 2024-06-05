plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    //SafeArgs


    //parcelable
    id("kotlin-parcelize")

    //id("androidx.navigation.safeargs.kotlin")

    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.recyclerviewwithnavigationcomponent"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.recyclerviewwithnavigationcomponent"
        minSdk = 28
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    //View Binding Dependencies
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":di"))


    val fragment_version = "1.6.2"
    val nav_version = "2.7.7"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    /*Navigation Component*/
    // Java language implementation
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // Feature module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Testing Navigation
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$nav_version")
    /*End Navigation Component*/

    //Fragment
    // Java
    implementation("androidx.fragment:fragment:$fragment_version")
    // Kotlin
    implementation("androidx.fragment:fragment-ktx:$fragment_version")
    //End Fragment

    /*RecyclerView*/
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    /*End RecyclerView*/

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.9.0")

    //room
    implementation(libs.androidx.roomRuntime)
    ksp(libs.androidx.roomCompilerKsp)
    implementation(libs.androidx.roomCoroutines)


    //retrofit dkk
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation (libs.gson)

    //coil
    implementation("io.coil-kt:coil:2.6.0")
    implementation ("com.google.android.material:material:1.8.0")

    //dataStore
    implementation(libs.androidx.datastore)

    //koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    //workmanager
    implementation(libs.androidx.workmanager)
    implementation(libs.androidx.core)

    //filepicker
    implementation(libs.filepicker)
    implementation("androidx.work:work-runtime-ktx:2.7.1")

    //CircleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")


}