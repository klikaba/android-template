package ba.klika.androidtemplate.data.base.di.module

import ba.klika.androidtemplate.data.base.config.DefaultNetworkConfig
import ba.klika.androidtemplate.data.base.config.NetworkConfig
import ba.klika.androidtemplate.data.base.di.api.ApiFactory
import ba.klika.androidtemplate.data.base.di.api.RetrofitApiFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Module which should provide core networking dependencies:
 *  * Http client (OkHttp)
 *  * ApiFactory (Retrofit)
 *  * Core interceptors (Http logging interceptor and authentication if needed)
 *  * Authenticator
 *  * Dispatcher
 *
 * Additional interceptors may be provided from other modules, but they must be at [Singleton] scope
 * They also need to be provided [IntoSet] since many interceptors may be used
 * Order of adding interceptors is not enforced!
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Module
abstract class NetworkModule {
    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideAuthenticator(): Authenticator {
            return Authenticator.NONE
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideDispatcher(): Dispatcher {
            return Dispatcher()
        }

        @Provides
        @Singleton
        @IntoSet
        @JvmStatic
        fun provideLoggingInterceptor(): Interceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideOkHttpClient(networkConfig: NetworkConfig,
                                authenticator: Authenticator,
                                dispatcher: Dispatcher,
                                interceptors: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                    .authenticator(authenticator)
                    .dispatcher(dispatcher)

            interceptors.forEach { builder.addInterceptor(it) }

            return builder
                    .connectTimeout(networkConfig.connectTimeoutInMs, TimeUnit.MILLISECONDS)
                    .readTimeout(networkConfig.readTimeoutInMs, TimeUnit.MILLISECONDS)
                    .writeTimeout(networkConfig.writeTimeoutInMs, TimeUnit.MILLISECONDS)
                    .build()
        }
    }

    @Binds
    @Singleton
    abstract fun provideRetrofitApiFactory(retrofitApiFactory: RetrofitApiFactory): ApiFactory

    @Binds
    @Singleton
    abstract fun provideDefaultNetworkConfig(defaultNetworkConfig: DefaultNetworkConfig): NetworkConfig
}
