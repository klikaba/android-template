package ba.klika.androidtemplate.ui.base.di.module

import ba.klika.androidtemplate.BuildConfig
import ba.klika.androidtemplate.data.auth.ClientConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Module
object AppConfigModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideClientConfig(): ClientConfig = object : ClientConfig {
        override val clientId: String
            get() = BuildConfig.clientId
        override val clientSecret: String
            get() = BuildConfig.clientSecret
    }
}