import org.gradle.api.JavaVersion

object Dependencies {
    object Language {
        object Versions {
            const val kotlinVersion = "1.4.10"
            val javaSourceCompatibility = JavaVersion.VERSION_1_8
            val javaTargetCompatibility = JavaVersion.VERSION_1_8
            const val kotlinCoroutinesVersion = "1.4.0-M1"
        }
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
        const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutinesVersion}"
    }

    object Gradle {
        object Versions {
            const val androidGradlePluginVersion = "4.1.0"
        }
        const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePluginVersion}"
    }

    object AndroidX {
        object Versions {
            const val appCompatVersion = "1.2.0"
            const val androidXActivity = "1.1.0"
            const val androidAnnotationVersion = "1.1.0"
            const val recyclerViewVersion = "1.1.0"
            const val googleMaterialVersion = "1.0.0"
            const val androidLifecycleVersion = "2.2.0"
            const val constraintLayoutVersion = "2.0.2"
            const val archComponentsVersion = "2.1.0"
            const val navigationVersion = "2.3.1"
        }
        const val androidLifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.androidLifecycleVersion}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
        const val androidAnnotation = "androidx.annotation:annotation:${Versions.androidAnnotationVersion}"
        const val androidXActivity = "androidx.activity:activity-ktx:${Versions.androidXActivity}"
        const val navigationFragment = "android.arch.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
        const val navigationUi = "android.arch.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
        const val googleMaterial = "com.google.android.material:material:${Versions.googleMaterialVersion}"
        const val androidLifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidLifecycleVersion}"
        const val androidLifecycleReactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:${Versions.androidLifecycleVersion}"
    }

    object Networking {
        object Versions {
            const val okhttp3Version = "4.9.0"
            const val retrofit2Version = "2.9.0"
        }
        const val okHttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3Version}"
        const val okHttp3Logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Version}"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2Version}"
        const val retrofit2GsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2Version}"
    }

    object Database {
        object Versions {
            const val roomVersion = "2.1.0"
        }
        const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    }

    object DependencyInjections {
        object Versions {
            const val javaxInjectVersion = "1"
            const val javaxAnnotationVersion = "1.0"
            const val daggerVersion = "2.22.1"
        }
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerVersion}"
        const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.daggerVersion}"
        const val dagger = "com.google.dagger:dagger:${Versions.daggerVersion}"
        const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.daggerVersion}"
        const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.daggerVersion}"
        const val javaxAnnotation = "javax.annotation:jsr250-api:${Versions.javaxAnnotationVersion}"
        const val javaxInject = "javax.inject:javax.inject:${Versions.javaxInjectVersion}"
    }

    object Other {
        object Versions {
            const val gsonVersion = "2.8.2"
        }
        const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
    }

    object Testing {
        object Versions {
            const val junitVersion = "4.12"
            const val robolectricVersion = "3.6.1"
            const val assertJVersion = "3.9.0"
            const val androidJunit5Version = "1.6.2.0"
            const val jacocoVersion = "0.8.1"
            const val mockKVersion = "1.10.2"
            const val jupiterVersion = "5.4.2"
        }
        const val junit = "junit:junit:${Versions.junitVersion}"
        const val mockK = "io.mockk:mockk:${Versions.mockKVersion}"
        const val assertJ = "org.assertj:assertj-core:${Versions.assertJVersion}"
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
        const val archCoreTesting = "androidx.arch.core:core-testing:${AndroidX.Versions.archComponentsVersion}"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Language.Versions.kotlinVersion}"
        const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Language.Versions.kotlinVersion}"
        const val androidJunit5GradlePlugin = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.androidJunit5Version}"
        const val jacocoGradlePlugin = "org.jacoco:org.jacoco.core:${Versions.jacocoVersion}"
        const val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Language.Versions.kotlinCoroutinesVersion}"
        const val jupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.jupiterVersion}"
        const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.jupiterVersion}"
    }
}
