package ba.klika.androidtemplate.data.base.store

/**
 * Simple key value storage interface
 *
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface SimpleKeyValueStore {

    fun put(key: String, value: Set<String>)

    fun put(key: String, value: String)

    fun put(key: String, value: Boolean)

    fun put(key: String, value: Int)

    fun put(key: String, value: Long)

    fun hasStored(key: String): Boolean

    fun delete(key: String)

    fun readStringSet(key: String, defaultValue: Set<String>): Set<String>

    fun readString(key: String, defaultValue: String): String

    fun readBoolean(key: String, defaultValue: Boolean): Boolean

    fun readInt(key: String, defaultValue: Int): Int

    fun readLong(key: String, defaultValue: Long): Long

    fun readStringSet(key: String): Set<String>?

    fun readString(key: String): String?

    fun readBoolean(key: String): Boolean?

    fun readInt(key: String): Int?

    fun readLong(key: String): Long?
}
