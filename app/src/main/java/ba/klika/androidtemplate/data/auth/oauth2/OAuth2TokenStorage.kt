package ba.klika.androidtemplate.data.auth.oauth2

import ba.klika.androidtemplate.data.auth.TokenStorage
import ba.klika.androidtemplate.data.base.store.SimpleKeyValueStore
import javax.inject.Inject

/**
 * Simple storage for OAuth2 Tokens (there should always be one currently)
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class OAuth2TokenStorage
@Inject constructor(private val simpleKeyValueStore: SimpleKeyValueStore) : TokenStorage<OAuth2Token> {

    override fun clearToken() {
        simpleKeyValueStore.delete(ACCESS_TOKEN_KEY)
        simpleKeyValueStore.delete(REFRESH_TOKEN_KEY)
        simpleKeyValueStore.delete(CREATED_AT_KEY)
        simpleKeyValueStore.delete(EXPIRATION_PERIOD_KEY)
    }

    override fun saveToken(token: OAuth2Token) {
        simpleKeyValueStore.put(EXPIRATION_PERIOD_KEY, token.expirationPeriod)
        simpleKeyValueStore.put(CREATED_AT_KEY, token.createdAt)
        simpleKeyValueStore.put(ACCESS_TOKEN_KEY, token.accessToken)
        simpleKeyValueStore.put(REFRESH_TOKEN_KEY, token.refreshToken)
    }

    override fun readToken(): OAuth2Token? {
        val accessToken = simpleKeyValueStore.readString(ACCESS_TOKEN_KEY)
        val refreshToken = simpleKeyValueStore.readString(REFRESH_TOKEN_KEY)
        return if (accessToken == null || refreshToken == null) {
            null
        } else OAuth2Token(
            simpleKeyValueStore.readLong(EXPIRATION_PERIOD_KEY, 0L),
            simpleKeyValueStore.readLong(CREATED_AT_KEY, 0L),
            accessToken,
            refreshToken
        )
    }

    companion object {
        // Tag as string if we use proguard in future
        const val TAG = "OAuth2TokenStorage"
        const val EXPIRATION_PERIOD_KEY = "$TAG.expiration_period"
        const val ACCESS_TOKEN_KEY = "$TAG.access_token"
        const val REFRESH_TOKEN_KEY = "$TAG.refresh_token"
        const val CREATED_AT_KEY = "$TAG.created_at"
    }
}
