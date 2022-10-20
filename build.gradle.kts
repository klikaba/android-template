// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        maven("https://maven.google.com")
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Gradle.androidGradlePlugin)
        classpath(Dependencies.Language.kotlinGradlePlugin)
        classpath(Dependencies.Testing.androidJunit5GradlePlugin)
        classpath(Dependencies.Testing.jacocoGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        maven("https://maven.google.com")
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
