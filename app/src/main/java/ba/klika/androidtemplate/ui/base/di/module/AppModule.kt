package ba.klika.androidtemplate.ui.base.di.module

import android.app.Application
import android.content.Context
import ba.klika.androidtemplate.App
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * This module should provide high level, application specific dependencies
 *
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Module(subcomponents = [])
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideApplication(app: App): Application

    @Binds
    @Singleton
    abstract fun provideContext(application: Application): Context
}
