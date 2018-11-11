package ba.klika.androidtemplate.data.session

import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenApi
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenStorage
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2RequestFactory
import ba.klika.androidtemplate.data.base.di.module.Authenticated
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface SessionRepository {
    fun logIn(credentials: Credentials): Completable

    fun logOut(): Completable

    fun hasSession(): Single<Boolean>
}

class SessionRepositoryImpl
@Inject constructor(@param:Authenticated(false) private val oAuth2TokenApi: OAuth2TokenApi,
                    private val oAuth2TokenStorage: OAuth2TokenStorage,
                    private val oAuth2RequestFactory: OAuth2RequestFactory) : SessionRepository {
    override fun logIn(credentials: Credentials): Completable {
        return oAuth2TokenApi.createToken(
                oAuth2RequestFactory.makeCreateTokenRequest(
                        credentials.username,
                        credentials.password)
        ).doOnSuccess {
            oAuth2TokenStorage.saveToken(it)
        }.toCompletable()
    }

    override fun logOut(): Completable = Completable.fromAction { oAuth2TokenStorage.clearToken() }

    override fun hasSession(): Single<Boolean> = Single.fromCallable {
        oAuth2TokenStorage.readToken() != null
    }
}