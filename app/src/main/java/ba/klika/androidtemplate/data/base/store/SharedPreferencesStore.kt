package ba.klika.androidtemplate.data.base.store

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Singleton
class SharedPreferencesStore
@Inject constructor(private val sharedPreferences: SharedPreferences) : SimpleKeyValueStore {

    private val editor: SharedPreferences.Editor
        get() = sharedPreferences.edit()

    override fun put(key: String, value: Set<String>) {
        val editor = editor
        editor.putStringSet(key, value)
        editor.apply()
    }

    override fun put(key: String, value: String) {
        val editor = editor
        editor.putString(key, value)
        editor.apply()
    }

    override fun put(key: String, value: Boolean) {
        val editor = editor
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun put(key: String, value: Int) {
        val editor = editor
        editor.putInt(key, value)
        editor.apply()
    }

    override fun put(key: String, value: Long) {
        val editor = editor
        editor.putLong(key, value)
        editor.apply()
    }

    override fun hasStored(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    override fun delete(key: String) {
        val editor = editor
        editor.remove(key)
        editor.apply()
    }

    override fun readStringSet(key: String, defaultValue: Set<String>): Set<String> = sharedPreferences.getStringSet(key, defaultValue)!!

    override fun readString(key: String, defaultValue: String) = sharedPreferences.getString(key, defaultValue)!!

    override fun readBoolean(key: String, defaultValue: Boolean) = sharedPreferences.getBoolean(key, defaultValue)

    override fun readInt(key: String, defaultValue: Int) = sharedPreferences.getInt(key, defaultValue)

    override fun readLong(key: String, defaultValue: Long) = sharedPreferences.getLong(key, defaultValue)

    override fun readStringSet(key: String): Set<String>? = sharedPreferences.getStringSet(key, HashSet())

    override fun readString(key: String): String? = sharedPreferences.getString(key, null)

    override fun readBoolean(key: String): Boolean? =
            if (hasStored(key)) sharedPreferences.getBoolean(key, false) else null

    override fun readInt(key: String): Int?  =
            if (hasStored(key)) sharedPreferences.getInt(key, 0) else null

    override fun readLong(key: String): Long? =
            if (hasStored(key)) sharedPreferences.getLong(key, 0L) else null
}