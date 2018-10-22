package ba.klika.androidtemplate.data.base.di.api

/**
 * This represents something that can produce implementation of API
 *
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface ApiFactory {

    /**
     * Should produce instance of [T]
     */
    fun <T> buildApi(type: Class<T>): T
}
