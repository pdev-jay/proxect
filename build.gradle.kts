// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false

    id("com.google.dagger.hilt.android") version "2.56.2" apply false
    // KSP 플러그인, 버전은 프로젝트의 kotlin 버전과 맞는 버전 채택 : https://github.com/google/ksp/releases
    id("com.google.devtools.ksp") version "2.0.0-1.0.23" apply false

    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}