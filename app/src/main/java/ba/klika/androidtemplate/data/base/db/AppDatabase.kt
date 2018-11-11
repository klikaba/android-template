package ba.klika.androidtemplate.data.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ba.klika.androidtemplate.data.country.CountriesDao
import ba.klika.androidtemplate.data.country.Country
import ba.klika.androidtemplate.data.user.User
import ba.klika.androidtemplate.data.user.UsersDao
import java.util.Date
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Singleton
@TypeConverters(value = [DateTypeConverter::class])
@Database(entities = [Country::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countriesDao(): CountriesDao
    abstract fun usersDao(): UsersDao
}

object DateTypeConverter {

    @TypeConverter
    @JvmStatic
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    @JvmStatic
    fun toLong(value: Date?): Long? {
        return value?.time
    }
}
