package ba.klika.androidtemplate.data.auth.oauth2;

import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2CreateTokenRequest;
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2RefreshTokenRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * OAuth2 Token related APIs
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
public interface OAuth2TokenApi {

    @POST("oauth/token")
    Single<OAuth2Token> createToken(@Body OAuth2CreateTokenRequest createTokenRequest);

    @POST("oauth/token")
    Single<OAuth2Token> refreshToken(@Body OAuth2RefreshTokenRequest refreshTokenRequest);
}
