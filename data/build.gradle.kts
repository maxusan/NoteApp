plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "code.banana.todo_app"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        consumerProguardFiles("proguard-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))

    implementation(Dependencies.coreCtx)
    implementation(Dependencies.lifecycleRuntime)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.extJunit)
    androidTestImplementation(Dependencies.espresso)

    // Dagger/Hilt
    implementation(Dependencies.daggerHilt)
    ksp(Dependencies.daggerCompiler)

    // Room
    implementation(Dependencies.room)
    ksp(Dependencies.roomCompiler)

    //datastore
    implementation(Dependencies.datastore)
    implementation(Dependencies.datastorePreferences)
}