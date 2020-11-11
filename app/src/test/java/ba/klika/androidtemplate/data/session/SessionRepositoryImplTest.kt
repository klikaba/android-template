package ba.klika.androidtemplate.data.session

import ba.klika.androidtemplate.data.auth.ClientConfig
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2Token
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenApi
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2TokenStorage
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2CreateTokenRequest
import ba.klika.androidtemplate.data.auth.oauth2.request.OAuth2RequestFactory
import ba.klika.androidtemplate.data.base.store.InMemoryKeyValueStore
import ba.klika.androidtemplate.data.base.store.SimpleKeyValueStore
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.fail
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SessionRepositoryImplTest {
    val mockTokenApi = mockk<OAuth2TokenApi>()
    lateinit var kvStorage: SimpleKeyValueStore
    lateinit var tokenStorage: OAuth2TokenStorage
    val requestFactory = OAuth2RequestFactory(object : ClientConfig {
        override val clientId: String = "fake"
        override val clientSecret: String = "fake"
    })
    lateinit var sessionRepository: SessionRepositoryImpl

    inner class BadCredentialsException : Exception("Bad credentials")

    val badCredentialsError = BadCredentialsException()

    @BeforeEach
    fun setUp() {
        kvStorage = InMemoryKeyValueStore()
        tokenStorage = OAuth2TokenStorage(kvStorage)
        sessionRepository = SessionRepositoryImpl(
            mockTokenApi,
            tokenStorage,
            requestFactory
        )
    }

    @AfterEach
    fun resetMocks() {
        clearMocks(mockTokenApi)
    }

    @Nested
    @DisplayName("Given working OAuth2TokenApi")
    inner class WorkingApi {
        @BeforeEach
        internal fun setUp() {
            coEvery { mockTokenApi.createToken(any()) } answers {
                val request: OAuth2CreateTokenRequest = arg(0)
                if (request.username == "username" && request.password == "password")
                    OAuth2Token(123, 456, "abc", "def")
                else throw badCredentialsError
            }
        }

        @Nested
        @DisplayName("Given correct credentials")
        inner class CorrectCredentials {
            val credentials = Credentials("username", "password")

            @Nested
            @DisplayName("When log in is called")
            inner class OnRepositoryLogIn {

                @BeforeEach
                internal fun setUp() = runBlocking {
                    sessionRepository.logIn(credentials)
                }

                @Test
                internal fun `Then log in should be requested from api`() {
                    val requestSlot = slot<OAuth2CreateTokenRequest>()
                    coVerify { mockTokenApi.createToken(capture(requestSlot)) }
                    requestSlot.captured.also {
                        assertEquals(credentials.username, it.username)
                        assertEquals(credentials.password, it.password)
                    }
                }

                @Test
                internal fun `Then token should be saved to storage`() {
                    val token = tokenStorage.readToken() ?: fail("Token not found")
                    token.also {
                        assertEquals(123, it.expirationPeriod)
                        assertEquals(456, it.createdAt)
                        assertEquals("abc", it.accessToken)
                        assertEquals("def", it.refreshToken)
                    }
                }
            }
        }

        @Nested
        @DisplayName("Given incorrect credentials")
        inner class IncorrectCredentials {
            val credentials = Credentials("wrong", "wrong")

            @Nested
            @DisplayName("When log in is called")
            inner class OnRepositoryLogIn {

                @Test
                internal fun `Then error should be passed down from api`() = runBlocking<Unit> {
                    assertThrows<BadCredentialsException> {
                        runBlocking {
                            sessionRepository.logIn(credentials)
                        }
                    }
                }
            }
        }

        @Nested
        @DisplayName("Given stored token")
        inner class StoredToken {
            @BeforeEach
            internal fun setUp() {
                // Save fake token
                tokenStorage.saveToken(OAuth2Token(123, 456, "accToken", "refrToken"))
                assertNotNull(tokenStorage.readToken())
            }

            @Nested
            @DisplayName("When log out is called")
            inner class OnRepositoryLogOut {
                @BeforeEach
                internal fun setUp() = runBlocking {
                    sessionRepository.logOut()
                }

                @Test
                internal fun `Then token should be deleted`() {
                    assertNull(tokenStorage.readToken())
                }
            }

            @Nested
            @DisplayName("When session is checked")
            inner class OnSessionCheck {
                @Test
                internal fun `It should return true`() = runBlocking {
                    assertTrue(sessionRepository.hasSession())
                }
            }
        }

        @AfterEach
        internal fun tearDown() {
        }
    }

    @Nested
    @DisplayName("Given failing OAuth2TokenApi")
    inner class FailingApi {
        @BeforeEach
        internal fun setUp() {
            coEvery { mockTokenApi.createToken(any()) } throws IOException("Failed")
        }

        @Nested
        @DisplayName("When log in is called")
        inner class OnRepositoryLogIn {

            @Test
            internal fun `Then it should pass down error from api`() {
                assertThrows<IOException> {
                    runBlocking {
                        sessionRepository.logIn(Credentials("any", "any"))
                    }
                }
            }

            @Test
            internal fun `It should not store any token`() = runBlocking {
                sessionRepository.runCatching { sessionRepository.logIn(Credentials("any", "any")) }
                assertNull(tokenStorage.readToken())
            }
        }
    }
}
