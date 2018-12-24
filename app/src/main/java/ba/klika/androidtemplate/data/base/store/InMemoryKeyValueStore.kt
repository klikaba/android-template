package ba.klika.androidtemplate.data.base.store

import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@sleepnumber.com>.
 */
class InMemoryKeyValueStore
@Inject constructor() : SimpleKeyValueStore {

    private val map = HashMap<String, Any>()

    override fun put(key: String, value: Set<String>) = store(key, value)

    override fun put(key: String, value: String) = store(key, value)

    override fun put(key: String, value: Boolean) = store(key, value)

    override fun put(key: String, value: Int) = store(key, value)

    override fun put(key: String, value: Long) = store(key, value)

    override fun hasStored(key: String): Boolean = map.containsKey(key)

    override fun delete(key: String) {
        map.remove(key)
    }

    override fun readStringSet(key: String, defaultValue: Set<String>): Set<String> = map[key] as Set<String>

    override fun readString(key: String, defaultValue: String): String = map[key] as String

    override fun readBoolean(key: String, defaultValue: Boolean): Boolean = map[key] as Boolean

    override fun readInt(key: String, defaultValue: Int): Int = map[key] as Int

    override fun readLong(key: String, defaultValue: Long): Long = map[key] as Long

    override fun readStringSet(key: String): Set<String>? = map[key] as? Set<String>

    override fun readString(key: String): String? = map[key] as? String

    override fun readBoolean(key: String): Boolean? = map[key] as Boolean?

    override fun readInt(key: String): Int? = map[key] as Int?

    override fun readLong(key: String): Long? = map[key] as Long?

    private fun store(key: String, value: Any) {
        map[key] = value
    }
}