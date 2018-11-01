package ba.klika.androidtemplate.data.session

import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenApi
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenStorage
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2CreateTokenRequest
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface SessionRepository {
    fun logIn(): Completable

    fun logOut(): Completable

    fun hasSession(): Single<Boolean>
}

class SessionRepositoryImpl
@Inject constructor(private val oAuth2TokenApi: OAuth2TokenApi,
                    private val oAuth2TokenStorage: OAuth2TokenStorage) : SessionRepository {
    override fun logIn(): Completable = oAuth2TokenApi.createToken(OAuth2CreateTokenRequest("","","","")).toCompletable()

    override fun logOut(): Completable = Completable.fromAction { oAuth2TokenStorage.clearToken() }

    override fun hasSession(): Single<Boolean> = Single.fromCallable { oAuth2TokenStorage.readToken() != null }
}