package ba.klika.androidtemplate.data.auth.oauth2.request

import com.google.gson.annotations.SerializedName

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class OAuth2RefreshTokenRequest(@field:SerializedName("refresh_token")
                                private val refreshToken: String,
                                clientId: String,
                                clientSecret: String) : OAuth2GrantRequest(GRANT_TYPE, clientId, clientSecret) {
    companion object {
        private const val GRANT_TYPE = "refresh_token"
    }
}
