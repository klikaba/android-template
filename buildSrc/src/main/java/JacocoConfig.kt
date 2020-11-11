object JacocoConfig {
    val fileFilter = mutableSetOf(
        "**/R.class",
        "**/BR.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Application*.*",
        "**/*Fragment*.*",
        "**/*Activity*.*",
        "**/*ViewHolder*.*",
        "**/DataBinderMapperImpl*.*",
        "**/android/**",
        "**/androidx/**",
        "**/*Component*.*",
        "**/*Module.*",
        "**/*Module_*",
        "**/*_Impl*",
        "**/*Dagger*.*",
        "**/*MembersInjector*.*",
        "**/ui/base/**",
        "**/data/base/**",
        "**/*Binding*.*",
        "**/*_Provide*Factory*.*",
        "**/*_Factory*.*",
        "**/*\$Lambda$*.*", // Jacoco can not handle several "$" in class name.
        "**/*\$inlined$*.*" // Kotlin specific, Jacoco can not handle several "$" in class name.
    )
}
