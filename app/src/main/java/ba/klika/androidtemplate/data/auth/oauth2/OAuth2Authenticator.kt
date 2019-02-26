package ba.klika.androidtemplate.data.auth.oauth2

import android.text.TextUtils
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable

import java.io.IOException

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

/**
 * OKHttp OAuth2 authenticator implementation
 * Made to fix request on 401 responses
 * Since we are checking tokens expiration time prior to sending requests and adding headers
 * to all requests with [OAuth2Interceptor], this class should not have much work
 * It will try to add missing header and log errors
 *
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class OAuth2Authenticator
@Inject constructor(
    private val oAuth2TokenStorage: OAuth2TokenStorage,
    private val oAuth2TokenRefresher: OAuth2TokenRefresher
) : Authenticator {

    @Nullable
    @Throws(IOException::class)
    override fun authenticate(@NonNull route: Route, @NonNull response: Response): Request? {
        val authHeader = response.request().header(AUTH_HEADER)
        if (authHeader == null) {
            // Request was missing auth header and received 401
            // This is very possibly an implementation bug, since interceptor should add header to calls
            Log.w(TAG, "Request was missing auth header! Route: " + route.toString())
            val oAuth2Token = oAuth2TokenStorage.readToken()
            return if (oAuth2Token != null) {
                retryWithToken(response, oAuth2Token.accessToken)
            } else {
                // No token, can't attempt auth
                null
            }
        }

        // There was an auth attempt but it failed
        if (authHeader.length < 7 || !authHeader.startsWith(AUTH_HEADER_PREFIX)) {
            // Bad auth request, we should not attempt to fix it
            Log.w(TAG, "Bad auth request was made. Authorization header: $authHeader")
            return null
        }

        // get token - remove prefix
        // compare with stored token
        val sentToken = authHeader.replaceFirst(AUTH_HEADER_PREFIX.toRegex(), "")
        val storedToken = oAuth2TokenStorage.readToken()

        if (storedToken == null) {
            Log.w(TAG, "No token stored. Can't attempt auth")
            return null
        }

        if (!TextUtils.equals(sentToken, storedToken.accessToken)) {
            // Sent token is not the same as stored one
            // Retry with stored one
            return retryWithToken(response, storedToken.accessToken)
        }

        if (!storedToken.expired()) {
            // Stored token is the same as sent one
            // But it hasn't expired
            // Seems like a bad token - abort
            Log.w(TAG, "Bad token stored")
            return null
        }

        // refresh token
        val newToken: OAuth2Token?
        newToken = oAuth2TokenRefresher.refreshToken()
        // retry with new token
        return retryWithToken(response, newToken.accessToken)
    }

    private fun retryWithToken(response: Response, accessToken: String): Request {
        return response.request().newBuilder()
                .removeHeader(AUTH_HEADER)
                .addHeader(AUTH_HEADER, "Bearer $accessToken")
                .build()
    }

    companion object {
        const val TAG = "OAuth2Authenticator"
        private const val AUTH_HEADER = "Authorization"
        private const val AUTH_HEADER_PREFIX = "Bearer "
    }
}
