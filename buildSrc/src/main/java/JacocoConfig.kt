object JacocoConfig {
    val fileFilter = mutableSetOf(
            "**/R.class",
            "**/R\$*.class",
            "**/databinding/*",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Application*.*",
            "**/*Fragment*.*",
            "**/*Activity*.*",
            "**/*ViewHolder*.*",
            "android/**/*.*",
            "**/*Component*.*",
            "**/*Module.*",
            "**/*Dagger*.*",
            "**/*MembersInjector*.*",
            "**/*Binding*.*",
            "**/*_Provide*Factory*.*",
            "**/*_Factory*.*",
            "**/*\$Lambda$*.*", // Jacoco can not handle several "$" in class name.
            "**/*\$inlined$*.*" // Kotlin specific, Jacoco can not handle several "$" in class name.
    )
}