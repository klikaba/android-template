package ba.klika.androidtemplate.data.base.di.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import ba.klika.androidtemplate.data.base.db.AppDatabase
import ba.klika.androidtemplate.data.base.store.SharedPreferencesStore
import ba.klika.androidtemplate.data.base.store.SimpleKeyValueStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module which should provide core caching/database dependencies:
 *  * Room database ([AppDatabase])
 *  * Shared preferences
 *
 * Dao implementations should be provided from feature specific modules
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Module
abstract class CacheModule {
    @Module
    companion object {
        private const val dbName: String = "main-db"
        @Singleton
        @Provides
        @JvmStatic
        fun provideDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideSharedPreferences(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Binds
    abstract fun provideSharedPreferencesStore(sharedPreferencesStore: SharedPreferencesStore): SimpleKeyValueStore
}
