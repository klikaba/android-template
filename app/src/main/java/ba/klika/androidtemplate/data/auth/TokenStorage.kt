package ba.klika.androidtemplate.data.auth

import ba.klika.androidtemplate.data.base.store.SimpleKeyValueStore
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface TokenStorage<TOKEN_TYPE : AuthToken> {
    fun readToken(): TOKEN_TYPE?

    fun clearToken()

    fun saveToken(token: TOKEN_TYPE)
}

class BasicTokenStorage
@Inject constructor(private val simpleKeyValueStore: SimpleKeyValueStore): TokenStorage<BasicAuthToken> {

    override fun readToken(): BasicAuthToken? {
        return simpleKeyValueStore.readString(ACCESS_TOKEN_KEY)?.let {
            BasicAuthToken(it)
        }
    }

    override fun clearToken() {
        simpleKeyValueStore.delete(ACCESS_TOKEN_KEY)
    }

    override fun saveToken(token: BasicAuthToken) {
        simpleKeyValueStore.put(ACCESS_TOKEN_KEY, token.stringValue)
    }

    companion object {
        const val TAG = "BasicTokenStorage"
        const val ACCESS_TOKEN_KEY = "$TAG.access_token"
    }
}