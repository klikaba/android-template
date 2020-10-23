object Dependencies {
    object Language {
        object Versions {
            const val kotlinVersion = "1.4.10"
            const val javaSourceCompatibility = "1.8"
            const val javaTargetCompatibility = "1.8"
        }
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    }

    object Gradle {
        object Versions {
            const val androidGradlePluginVersion = "4.1.0"
        }
        const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePluginVersion}"
    }

    object AndroidX {
        object Versions {
            const val appCompatVersion = "1.1.0-beta01"
            const val androidXActivity = "1.0.0-beta01"
            const val androidAnnotationVersion = "1.0.0"
            const val recyclerViewVersion = "1.0.0"
            const val googleMaterialVersion = "1.0.0"
            const val androidLifecycleVersion = "2.0.0"
            const val constraintLayoutVersion = "2.0.0-beta4"
            const val archComponentsVersion = "2.0.0-rc01"
            const val navigationVersion = "1.0.0"
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
            const val okhttp3Version = "3.10.0"
            const val retrofit2Version = "2.3.0"
        }
        const val okHttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3Version}"
        const val okHttp3Logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3Version}"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2Version}"
        const val retrofit2GsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2Version}"
        const val retrofit2RxJava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit2Version}"
    }

    object Database {
        object Versions {
            const val roomVersion = "2.1.0"
        }
        const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
        const val roomRxJava2 = "androidx.room:room-rxjava2:${Versions.roomVersion}"
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

    object ReactiveX {
        object Versions {
            const val rxJava2Version = "2.1.9"
            const val rxAndroidVersion = "2.1.1"
        }
        const val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2Version}"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"
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
            const val mockitoVersion = "2.10.0"
            const val spekVersion = "2.0.0-rc.1"
            const val androidJunit5Version = "1.6.2.0"
            const val jacocoVersion = "0.8.1"
        }
        const val junit = "junit:junit:${Versions.junitVersion}"
        const val mockito = "org.mockito:mockito-core:${Versions.mockitoVersion}"
        const val assertJ = "org.assertj:assertj-core:${Versions.assertJVersion}"
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
        const val archCoreTesting = "androidx.arch.core:core-testing:${AndroidX.Versions.archComponentsVersion}"
        const val spekDslJvm = "org.spekframework.spek2:spek-dsl-jvm:${Versions.spekVersion}"
        const val spekRunnerJunit5 = "org.spekframework.spek2:spek-runner-junit5:${Versions.spekVersion}"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Language.Versions.kotlinVersion}"
        const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Language.Versions.kotlinVersion}"
        const val androidJunit5GradlePlugin = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.androidJunit5Version}"
        const val jacocoGradlePlugin = "org.jacoco:org.jacoco.core:${Versions.jacocoVersion}"
    }
}