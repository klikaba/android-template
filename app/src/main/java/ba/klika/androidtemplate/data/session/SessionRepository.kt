package ba.klika.androidtemplate.data.session

import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenApi
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenStorage
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2RequestFactory
import ba.klika.androidtemplate.data.base.di.module.Authenticated
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface SessionRepository {
    suspend fun logIn(credentials: Credentials)

    suspend fun logOut()

    suspend fun hasSession(): Boolean
}

class SessionRepositoryImpl
@Inject constructor(
    @param:Authenticated(false) private val oAuth2TokenApi: OAuth2TokenApi,
    private val oAuth2TokenStorage: OAuth2TokenStorage,
    private val oAuth2RequestFactory: OAuth2RequestFactory
) : SessionRepository {
    override suspend fun logIn(credentials: Credentials) {
        oAuth2TokenApi.createToken(
                oAuth2RequestFactory.makeCreateTokenRequest(
                        credentials.username,
                        credentials.password)
        ).also { oAuth2TokenStorage.saveToken(it) }
    }

    override suspend fun logOut() = oAuth2TokenStorage.clearToken()

    override suspend fun hasSession() = oAuth2TokenStorage.readToken() != null
}