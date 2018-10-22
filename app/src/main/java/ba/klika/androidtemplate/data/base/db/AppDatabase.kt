package ba.klika.androidtemplate.data.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Singleton
@Database(entities = [], version = 1)
abstract class AppDatabase : RoomDatabase()
