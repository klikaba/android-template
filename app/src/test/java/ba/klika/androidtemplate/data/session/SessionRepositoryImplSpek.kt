package ba.klika.androidtemplate.data.session

import ba.klika.androidtemplate.data.auth.ClientConfig
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2Token
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenApi
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenStorage
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2CreateTokenRequest
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2RefreshTokenRequest
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2RequestFactory
import ba.klika.androidtemplate.data.base.store.InMemoryKeyValueStore
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.mockito.ArgumentMatcher

import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.describe
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class SessionRepositoryImplSpek : Spek({
    class FakeTokenApi : OAuth2TokenApi {
        var createTokenMatcher: ArgumentMatcher<OAuth2CreateTokenRequest> = ArgumentMatcher { false }
        var createTokenGoodResult: Single<OAuth2Token> = Single.error(IllegalStateException())
        var createTokenBadResult: Single<OAuth2Token> = Single.error(IllegalStateException())
        private var createTokenCalls = ArrayList<OAuth2CreateTokenRequest>()

        var refreshTokenMatcher: ArgumentMatcher<OAuth2RefreshTokenRequest> = ArgumentMatcher { false }
        var refreshTokenGoodResult: Single<OAuth2Token> = Single.error(IllegalStateException())
        var refreshTokenBadResult: Single<OAuth2Token> = Single.error(IllegalStateException())
        private var refreshTokenCalls = ArrayList<OAuth2RefreshTokenRequest>()

        override suspend fun createToken(createTokenRequest: OAuth2CreateTokenRequest): OAuth2Token =
                if (createTokenMatcher.matches(createTokenRequest)) {
                    createTokenGoodResult
                } else {
                    createTokenBadResult
                }.also {
                    createTokenCalls.add(createTokenRequest)
                }

        override suspend fun refreshToken(refreshTokenRequest: OAuth2RefreshTokenRequest): OAuth2Token =
                if (refreshTokenMatcher.matches(refreshTokenRequest)) {
                    refreshTokenGoodResult
                } else {
                    refreshTokenBadResult
                }.also {
                    refreshTokenCalls.add(refreshTokenRequest)
                }

        fun verifyCalledCreateToken(matcher: ArgumentMatcher<OAuth2CreateTokenRequest>) =
                createTokenCalls.find { matcher.matches(it) }?.let { true } ?: false

        fun verifyCalledRefreshToken(matcher: ArgumentMatcher<OAuth2RefreshTokenRequest>) =
                refreshTokenCalls.find { matcher.matches(it) }?.let { true } ?: false
    }

    describe("a session repository implementation") {
        context("working api") {
            val tokenApi by memoized(CachingMode.SCOPE) { FakeTokenApi() }
            val kvStorage by memoized(CachingMode.GROUP) { InMemoryKeyValueStore() }
            val tokenStorage by memoized { OAuth2TokenStorage(kvStorage) }
            val requestFactory by memoized {
                OAuth2RequestFactory(object : ClientConfig {
                    override val clientId: String = "fake"
                    override val clientSecret: String = "fake"
                })
            }
            val sessionRepository by memoized {
                SessionRepositoryImpl(
                        tokenApi,
                        tokenStorage,
                        requestFactory
                )
            }

            tokenApi.createTokenMatcher = ArgumentMatcher { it.username == "username" && it.password == "password" }
            tokenApi.createTokenGoodResult = Single.just(OAuth2Token(123, 456, "accToken", "refrToken"))
            tokenApi.createTokenBadResult = Single.error(IOException("bad credentials"))

            context("login call with correct credentials") {
                lateinit var loginObserver: TestObserver<Void>
                beforeEach {
                    val credentials = Credentials("username", "password")
                    val loginCall = sessionRepository.logIn(credentials)
                    loginObserver = loginCall.test()
                    loginObserver.awaitTerminalEvent()
                }

                it("should request log in from api") {
                    loginObserver
                            .assertNoErrors()
                            .assertComplete()
                    tokenApi.verifyCalledCreateToken(ArgumentMatcher { it.username == "username" && it.password == "password" })
                }

                it("should save received token to storage") {
                    val token = tokenStorage.readToken()
                    assertEquals(token?.expirationPeriod, 123)
                    assertEquals(token?.createdAt, 456)
                    assertEquals(token?.accessToken, "accToken")
                    assertEquals(token?.refreshToken, "refrToken")
                }
            }

            context("login call with wrong credentials") {
                lateinit var loginObserver: TestObserver<Void>
                beforeEach {
                    val credentials = Credentials("badUsername", "badPassword")
                    val loginCall = sessionRepository.logIn(credentials)
                    loginObserver = loginCall.test()
                    loginObserver.awaitTerminalEvent()
                }

                it("should pass down error from api") {
                    loginObserver
                            .assertError {
                                it is IOException && it.message == "bad credentials"
                            }
                }
            }

            context("logout call with stored token") {
                lateinit var logoutObserver: TestObserver<Void>
                beforeEach {
                    // Save fake token
                    tokenStorage.saveToken(OAuth2Token(123, 456, "accToken", "refrToken"))
                    assertNotNull(tokenStorage.readToken())

                    val logoutCall = sessionRepository.logOut()
                    logoutObserver = logoutCall.test()
                    logoutObserver.awaitTerminalEvent()
                }

                it("should delete token") {
                    assertNull(tokenStorage.readToken())
                }
            }

            context("checking for session with stored token") {
                lateinit var sessionObserver: TestObserver<Boolean>
                beforeEach {
                    // Save fake token
                    tokenStorage.saveToken(OAuth2Token(123, 456, "accToken", "refrToken"))
                    assertNotNull(tokenStorage.readToken())

                    val sessionCheckCall = sessionRepository.hasSession()
                    sessionObserver = sessionCheckCall.test()
                    sessionObserver.awaitTerminalEvent()
                }

                it("should return true") {
                    sessionObserver
                            .assertComplete()
                            .assertValue(true)
                }
            }

            context("checking for session without stored token") {
                lateinit var sessionObserver: TestObserver<Boolean>
                beforeEach {
                    // No token
                    assertNull(tokenStorage.readToken())

                    val sessionCheckCall = sessionRepository.hasSession()
                    sessionObserver = sessionCheckCall.test()
                    sessionObserver.awaitTerminalEvent()
                }

                it("should return false") {
                    sessionObserver
                            .assertComplete()
                            .assertValue(false)
                }
            }
        }

        context("failing api") {
            val tokenApi by memoized(CachingMode.SCOPE) { FakeTokenApi() }
            val kvStorage by memoized(CachingMode.GROUP) { InMemoryKeyValueStore() }
            val tokenStorage by memoized { OAuth2TokenStorage(kvStorage) }
            val requestFactory by memoized {
                OAuth2RequestFactory(object : ClientConfig {
                    override val clientId: String = "fake"
                    override val clientSecret: String = "fake"
                })
            }
            val sessionRepository by memoized {
                SessionRepositoryImpl(
                        tokenApi,
                        tokenStorage,
                        requestFactory
                )
            }

            tokenApi.createTokenMatcher = ArgumentMatcher { it.username == "username" && it.password == "password" }
            tokenApi.createTokenGoodResult = Single.error(IOException("network error"))
            tokenApi.createTokenBadResult = Single.error(IOException("bad credentials"))

            context("login call with correct credentials") {
                lateinit var loginObserver: TestObserver<Void>
                beforeEach {
                    // No token
                    assertNull(tokenStorage.readToken())

                    val credentials = Credentials("username", "password")
                    val loginCall = sessionRepository.logIn(credentials)
                    loginObserver = loginCall.test()
                    loginObserver.awaitTerminalEvent()
                }

                it("should pass down error from api") {
                    loginObserver
                            .assertError {
                                it is IOException && it.message == "network error"
                            }
                    tokenApi.verifyCalledCreateToken(ArgumentMatcher { it.username == "username" && it.password == "password" })
                }

                it("should not store any token") {
                    // No token
                    assertNull(tokenStorage.readToken())
                }
            }

            context("login call with wrong credentials") {
                lateinit var loginObserver: TestObserver<Void>
                beforeEach {
                    val credentials = Credentials("badUsername", "badPassword")
                    val loginCall = sessionRepository.logIn(credentials)
                    loginObserver = loginCall.test()
                    loginObserver.awaitTerminalEvent()
                }

                it("should pass down error from api") {
                    loginObserver
                            .assertError {
                                it is IOException && it.message == "bad credentials"
                            }
                }
            }
        }
    }
})
