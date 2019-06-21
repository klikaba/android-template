package ba.klika.androidtemplate.data.session

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Module
abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun provideDefaultSessionRepository(
        sessionRepositoryImpl: SessionRepositoryImpl
    ): SessionRepository
}