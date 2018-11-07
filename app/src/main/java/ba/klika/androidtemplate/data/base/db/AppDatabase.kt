package ba.klika.androidtemplate.data.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ba.klika.androidtemplate.data.country.CountriesDao
import ba.klika.androidtemplate.data.country.Country
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Singleton
@Database(entities = [Country::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countriesDao(): CountriesDao
}
