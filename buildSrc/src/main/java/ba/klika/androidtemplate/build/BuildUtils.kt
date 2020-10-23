package ba.klika.androidtemplate.build

import java.io.File
import java.io.FileNotFoundException
import java.util.*

object BuildUtils {
    fun File.toProperties() = Properties().also { props ->
        inputStream().use { props.load(it) }
    }

    fun loadProperties(projectDir: String, buildType: String): Properties {
        val filename = "$projectDir/$buildType.properties"
        try {
            return File(filename).toProperties()
        } catch (e: FileNotFoundException) {
            throw Exception("Config not found ($filename). Check out config-example.properties file.")
        }
    }

    fun loadPropertiesIntoMap(projectDir: String, buildType: String): Map<String, String> =
            loadProperties(projectDir, buildType)
                    .map { (key, value) -> (key as String) to (value as String) }
                    .toMap()
}