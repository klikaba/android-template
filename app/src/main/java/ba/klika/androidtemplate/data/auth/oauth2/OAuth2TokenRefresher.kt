package ba.klika.androidtemplate.data.auth.oauth2

import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2RequestFactory
import ba.klika.androidtemplate.data.base.di.module.Authenticated
import javax.inject.Inject
import kotlin.NullPointerException

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class OAuth2TokenRefresher
@Inject constructor(
        private val tokenStorage: OAuth2TokenStorage,
        private val oAuth2RequestFactory: OAuth2RequestFactory,
        private val oAuth2TokenApi: Lazy<OAuth2TokenApi>) {

    fun refreshToken(): OAuth2Token {
        var newToken: OAuth2Token?
        synchronized(lock) {
            val oldToken = tokenStorage.readToken()
            return if (oldToken != null && oldToken.expired()) {
                newToken = oAuth2TokenApi.value.refreshToken(
                        oAuth2RequestFactory.makeRefreshTokenRequest(oldToken.refreshToken)
                ).blockingGet()
                // Response body should not be null if it was successful
                assert(newToken != null)
                tokenStorage.saveToken(newToken!!)
                newToken!!
            } else {
                if (oldToken == null) {
                    // TODO custom exception
                    throw NullPointerException("No token")
                }
                oldToken
            }
        }
    }

    companion object {
        private val lock = Any()
    }
}
