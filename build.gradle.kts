// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
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
        jcenter()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
