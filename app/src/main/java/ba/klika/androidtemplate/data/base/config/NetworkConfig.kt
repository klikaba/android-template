package ba.klika.androidtemplate.data.base.config

import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface NetworkConfig {

    val baseUrl: String
    val connectTimeoutInMs: Long
    val readTimeoutInMs: Long
    val writeTimeoutInMs: Long
}

@Singleton
class DefaultNetworkConfig
@Inject constructor(): NetworkConfig {
    override val baseUrl: String = "https://klika-rails-api.herokuapp.com"
    override val connectTimeoutInMs: Long = DEFAULT_TIMEOUT_MS
    override val readTimeoutInMs: Long = DEFAULT_TIMEOUT_MS
    override val writeTimeoutInMs: Long = DEFAULT_TIMEOUT_MS

    companion object {
        private const val DEFAULT_TIMEOUT_MS = 10000L
    }
}