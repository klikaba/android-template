package ba.klika.androidtemplate.data.auth.oauth2

import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2CreateTokenRequest
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2RefreshTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * OAuth2 Token related APIs
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface OAuth2TokenApi {

    @POST("oauth/token")
    suspend fun createToken(@Body createTokenRequest: OAuth2CreateTokenRequest): OAuth2Token

    @POST("oauth/token")
    suspend fun refreshToken(@Body refreshTokenRequest: OAuth2RefreshTokenRequest): OAuth2Token
}
