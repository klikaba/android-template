package ba.klika.androidtemplate.data.auth.oauth2

import androidx.annotation.NonNull
import kotlinx.coroutines.runBlocking
import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * OKHttp2 interceptor for OAuth2 authorization
 * It will add Authorization header and refresh token when needed
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */

class OAuth2Interceptor
@Inject constructor(
    private val oAuth2TokenStorage: OAuth2TokenStorage,
    private val oAuth2TokenRefresher: OAuth2TokenRefresher
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        var request = chain.request()

        // Ignore token calls to prevent loops
        if (request.url.toString().contains("oauth/token")) {
            return chain.proceed(request)
        }

        var oAuth2Token = oAuth2TokenStorage.readToken()
        // If there is a token, add it as header, otherwise proceed
        // because there may be some calls which don't require auth
        if (oAuth2Token != null) {
            // has token expired?
            if (oAuth2Token.expired()) {
                oAuth2Token = runBlocking { oAuth2TokenRefresher.refreshToken() }
            }

            request = request.newBuilder()
                    .removeHeader(AUTH_HEADER)
                    .addHeader(AUTH_HEADER, "Bearer " + oAuth2Token.accessToken)
                    .build()
        }

        return chain.proceed(request)
    }

    companion object {
        private const val AUTH_HEADER = "Authorization"
    }
}
