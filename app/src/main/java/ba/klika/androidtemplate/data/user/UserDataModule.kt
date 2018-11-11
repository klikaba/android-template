package ba.klika.androidtemplate.data.user

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
abstract class UserDataModule {

    @Binds
    @Singleton
    abstract fun provideDefaultUserRepository(
            apiUserRepository: ApiUserRepository): UserRepository

    @Module
    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideUsersDao(appDatabase: AppDatabase): UsersDao = appDatabase.usersDao()

        @Provides
        @Singleton
        @JvmStatic
        fun provideUsersApi(apiFactory: ApiFactory): UserApi = apiFactory.buildApi(UserApi::class.java)
    }
}
