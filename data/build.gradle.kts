import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)

    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
}
val properties = Properties()
properties.load(FileInputStream("local.properties"))

android {

    namespace = "com.pdevjay.proxect.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "SUPABASE_URL", "\"${properties.getProperty("SUPABASE_URL")}\"")
        buildConfigField("String", "SUPABASE_KEY", "\"${properties.getProperty("SUPABASE_KEY")}\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Room 예시
    implementation("androidx.room:room-ktx:2.6.1")
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")


    // Hilt 안드로이드 코어
    implementation("com.google.dagger:hilt-android:2.56.2")
    // Hilt 컴파일러 (KSP)
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")

    // Compose → Hilt ViewModel 통합
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    // Hilt Navigation 컴파일러 (KSP)
    ksp("androidx.hilt:hilt-compiler:1.2.0")

    implementation(project(":domain"))

    // supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:3.1.4"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")

    // serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

    // ktor
    val KTOR_VERSION="3.1.3"
    implementation("io.ktor:ktor-client-okhttp:$KTOR_VERSION")

}