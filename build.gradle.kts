// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        //val nav_version = "2.7.7"
        //classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kspPlugins) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.androidLibrary) apply false

    id("land.sungbin.dependency.graph.plugin") version "1.1.0"}

dependencyGraphConfig {
    projectName = null
    outputFormat = OutputFormat.PNG
    dependencyBuilder {
        null
    }
}