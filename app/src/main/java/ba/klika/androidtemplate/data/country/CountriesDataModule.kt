package ba.klika.androidtemplate.data.country

import ba.klika.androidtemplate.data.base.db.AppDatabase
import ba.klika.androidtemplate.data.base.di.api.ApiFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Module
abstract class CountriesDataModule {

    @Binds
    @Singleton
    abstract fun provideDefaultCountriesRepository(
            apiCountriesRepository: ApiCountriesRepository): CountriesRepository

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideCountriesDao(appDatabase: AppDatabase): CountriesDao = appDatabase.countriesDao()

        @Provides
        @Singleton
        @JvmStatic
        fun provideCountriesApi(apiFactory: ApiFactory): CountriesApi = apiFactory.buildApi(CountriesApi::class.java)
    }
}
