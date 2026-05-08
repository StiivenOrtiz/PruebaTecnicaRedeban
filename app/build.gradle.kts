import java.io.FileInputStream
import java.util.Properties

fun getSecret(key: String, default: String): String {
    val env = System.getenv(key)
    if (!env.isNullOrBlank()) return env

    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        properties.load(FileInputStream(localPropertiesFile))
        val prop = properties.getProperty(key)
        if (!prop.isNullOrBlank()) return prop
    }

    return default
}

plugins {
    // Android
    alias(libs.plugins.android.application)

    // Kotlin
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)

    // Hilt
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}
android {
    namespace = "com.stiivenortiz.pruebatecnicaredeban"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.stiivenortiz.pruebatecnicaredeban"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.stiivenortiz.pruebatecnicaredeban.HiltTestRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(
                "String",
                "BASE_URL",
                "\"${getSecret("BASE_URL", "https://default.com/")}\""
            )

            buildConfigField(
                "String",
                "AUTHORIZATION",
                "\"${getSecret("AUTHORIZATION", "n/a")}\""
            )
        }

        getByName("debug") {
            isDebuggable = true

            buildConfigField(
                "String",
                "BASE_URL",
                "\"${getSecret("BASE_URL", "https://test.com/")}\""
            )

            buildConfigField(
                "String",
                "AUTHORIZATION",
                "\"${getSecret("AUTHORIZATION", "n/a")}\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // Core Android
    implementation(libs.androidx.core.ktx)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Activity
    implementation(libs.androidx.activity.compose)

    // Compose / UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    // Icons--
    implementation(libs.androidx.compose.material.icons.core)
    // Constraint layout --
    implementation(libs.constraint.layout)
    // Google Font --
    implementation(libs.androidx.compose.ui.text.google.fonts)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // DI
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization)
    implementation(libs.retrofit.scalars)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    // Testing
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // DI testing
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // Mockk
    testImplementation(libs.mockk)

    // Coroutines Tests
    testImplementation(libs.kotlinx.coroutines.test)

    // Debug
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}