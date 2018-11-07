package ba.klika.androidtemplate.data.auth.oauth2

import ba.klika.androidtemplate.data.base.di.api.ApiFactory
import ba.klika.androidtemplate.data.base.di.module.Authenticated
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Interceptor
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Module
abstract class OAuth2Module {

    @Binds
    @Singleton
    @IntoSet
    @Authenticated(true)
    abstract fun provideOAuth2Interceptor(oAuth2Interceptor: OAuth2Interceptor): Interceptor

    @Binds
    @Singleton
    @Authenticated(true)
    abstract fun provideOAuth2Authenticator(oAuth2Authenticator: OAuth2Authenticator): Authenticator

    @Binds
    @Singleton
    abstract fun provideDefaultOAuth2TokenApi(
            @Authenticated(false) oAuth2TokenApi: OAuth2TokenApi): OAuth2TokenApi

    @Module
    companion object {
        @Provides
        @Singleton
        @Authenticated(true)
        @JvmStatic
        fun provideAuthenticatedOAuth2TokenApi(
                @Authenticated(true) apiFactory: ApiFactory
        ): OAuth2TokenApi = apiFactory.buildApi(OAuth2TokenApi::class.java)

        @Provides
        @Singleton
        @Authenticated(false)
        @JvmStatic
        fun provideUnauthenticatedOAuth2TokenApi(
                @Authenticated(false) apiFactory: ApiFactory
        ): OAuth2TokenApi = apiFactory.buildApi(OAuth2TokenApi::class.java)
    }
}