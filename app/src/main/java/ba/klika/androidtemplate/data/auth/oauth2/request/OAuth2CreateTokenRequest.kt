package ba.klika.androidtemplate.data.auth.oauth2.request

import com.google.gson.annotations.SerializedName

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class OAuth2CreateTokenRequest(@field:SerializedName("username")
                               val username: String,
                               @field:SerializedName("password")
                               val password: String,
                               clientId: String,
                               clientSecret: String) : OAuth2GrantRequest(GRANT_TYPE, clientId, clientSecret) {
    companion object {
        private const val GRANT_TYPE = "password"
    }
}
