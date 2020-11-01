import ba.klika.androidtemplate.build.BuildUtils.loadPropertiesIntoMap

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("jacoco")
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).configureEach {
    kotlinOptions {
        jvmTarget = Dependencies.Language.Versions.javaTargetCompatibility.toString()
    }
}

android {
    compileSdkVersion(AndroidConfig.Versions.androidCompileSdkVersion)
    defaultConfig {
        applicationId = "ba.klika.androidtemplate"
        minSdkVersion(AndroidConfig.Versions.androidMinSdkVersion)
        targetSdkVersion(AndroidConfig.Versions.androidTargetSdkVersion)
        versionCode = 1
        versionName = "1.0"
    }

    sourceSets.forEach {
        it.java.srcDirs("src/$it.name/kotlin")
    }

    buildTypes {
        all {
            loadPropertiesIntoMap(projectDir = projectDir.path, buildType = this.name).forEach { (k, v) ->
                buildConfigField("String", k, "\"$v\"")
            }
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    lintOptions {
        isAbortOnError = true
        isWarningsAsErrors = true
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = Dependencies.Language.Versions.javaSourceCompatibility
        targetCompatibility = Dependencies.Language.Versions.javaTargetCompatibility
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE*")
    }
}

tasks {
    withType<Test> {
        configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = true
        }
        useJUnitPlatform {
            includeEngines("spek2")
            testLogging {
                events = setOf(
                    org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
                    org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
                )
            }
        }
    }
    withType<JacocoReport> {
        reports {
            html.isEnabled = true
            xml.isEnabled = true
            csv.isEnabled = false
        }
        afterEvaluate {
            classDirectories.setFrom(files(classDirectories.files.map {
                fileTree(it) {
                    exclude(JacocoConfig.fileFilter)
                }
            }))
        }
    }
}

dependencies {
    implementation(Dependencies.Language.kotlinStdLib)
    implementation(Dependencies.Language.kotlinCoroutines)

    kapt(Dependencies.AndroidX.androidLifecycleCompiler)
    implementation(Dependencies.AndroidX.androidLifecycleExtensions)
    implementation(Dependencies.AndroidX.androidLifecycleReactiveStreams)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.androidAnnotation)
    implementation(Dependencies.AndroidX.androidXActivity)
    implementation(Dependencies.AndroidX.navigationFragment)
    implementation(Dependencies.AndroidX.navigationUi)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.recyclerView)
    implementation(Dependencies.AndroidX.googleMaterial)

    implementation(Dependencies.Networking.okHttp3)
    implementation(Dependencies.Networking.okHttp3Logging)
    implementation(Dependencies.Networking.retrofit2)
    implementation(Dependencies.Networking.retrofit2GsonConverter)

    kapt(Dependencies.Database.roomCompiler)
    implementation(Dependencies.Database.roomRuntime)
    implementation(Dependencies.Database.roomKtx)

    implementation(Dependencies.Other.gson)

    kapt(Dependencies.DependencyInjections.daggerCompiler)
    kapt(Dependencies.DependencyInjections.daggerAndroidProcessor)
    implementation(Dependencies.DependencyInjections.dagger)
    implementation(Dependencies.DependencyInjections.daggerAndroid)
    implementation(Dependencies.DependencyInjections.daggerAndroidSupport)
    implementation(Dependencies.DependencyInjections.javaxAnnotation)
    implementation(Dependencies.DependencyInjections.javaxInject)

    testImplementation(Dependencies.Testing.junit)
    testImplementation(Dependencies.Testing.mockK)
    testImplementation(Dependencies.Testing.assertJ)
    testImplementation(Dependencies.Testing.robolectric)
    testImplementation(Dependencies.Testing.kotlinReflect)
    testImplementation(Dependencies.Testing.kotlinTest)
    testImplementation(Dependencies.Testing.kotlinCoroutinesTest)
    testImplementation(Dependencies.Testing.archCoreTesting)
    testImplementation(Dependencies.Testing.jupiterApi)
    testRuntimeOnly(Dependencies.Testing.jupiterEngine)
}

kapt {
    javacOptions {
        // Increase the max count of errors from annotation processors.
        // Default is 100.
        option("-Xmaxerrs", 500)
    }
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas".toString())
    }
}