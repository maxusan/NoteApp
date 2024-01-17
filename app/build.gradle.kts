import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "code.banana.todo_app"
    compileSdk = AppConfig.compileSdk

    val versionPropsFile = file("version.properties")

    if (versionPropsFile.canRead()) {
        val versionProps = Properties()

        versionProps.load(FileInputStream(versionPropsFile))

        val code: Int = versionProps.getProperty("VERSION_CODE").toInt() + 1

        versionProps.setProperty("VERSION_CODE", code.toString())
        versionProps.store(versionPropsFile.writer(), null)

        defaultConfig {
            applicationId = "code.banana.todo_app"
            minSdk = AppConfig.minSdk
            targetSdk = AppConfig.targetSdk
            versionCode = AppConfig.versionCode
            versionName = AppConfig.versionName

            base.archivesBaseName = "$applicationId-v$versionName($code)"

            testInstrumentationRunner = AppConfig.androidTestInstrumentation
            vectorDrawables {
                useSupportLibrary = true
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":domain")))

    implementation(Dependencies.coreCtx)
    implementation(Dependencies.lifecycleRuntime)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.extJunit)
    androidTestImplementation(Dependencies.espresso)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.junitCompose)

    // compose
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeUI)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.navigationCompose)

    debugImplementation(Dependencies.composeUiToolingDebug)
    debugImplementation(Dependencies.composeUiTestManifest)

    // Lifecycle
    implementation(Dependencies.lifecycleRuntimeCompose)

    // Dagger/Hilt
    implementation(Dependencies.daggerHilt)
    ksp(Dependencies.daggerCompiler)
    implementation(Dependencies.daggerNavigationCompose)

    // Room
    implementation(Dependencies.room)
    ksp(Dependencies.roomCompiler)

}