package ba.klika.androidtemplate.data.country

import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okio.IOException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ApiCountriesRepositoryTest {
    val mockCountriesApi = mockk<CountriesApi>()
    val mockCountriesDao = mockk<CountriesDao>()
    lateinit var countriesRepository: ApiCountriesRepository

    @BeforeEach
    fun setUp() {
        countriesRepository = ApiCountriesRepository(
            mockCountriesApi,
            mockCountriesDao
        )
    }

    @AfterEach
    fun resetMocks() {
        clearMocks(mockCountriesApi)
        clearMocks(mockCountriesDao)
    }

    @Nested
    @DisplayName("Given working CountriesApi")
    inner class WorkingApi {
        val apiCountries = arrayListOf(
            Country("BiH", "BA"),
            Country("USA", "US")
        )

        @BeforeEach
        internal fun setUp() {
            coEvery { mockCountriesApi.all() } returns CountriesResponse(apiCountries)
        }

        @Nested
        @DisplayName("Given empty cache")
        inner class EmptyCache {
            @BeforeEach
            internal fun setUp() {
                coEvery { mockCountriesDao.countries() } throws NoSuchElementException()
                coEvery { mockCountriesDao.insertCountries(any()) } returns Unit
            }

            @Nested
            @DisplayName("When all countries are requested")
            inner class OnAllCountriesCall {
                lateinit var flowOutput: List<List<Country>>
                @BeforeEach
                internal fun setUp() = runBlocking {
                    flowOutput = countriesRepository.all().toList()
                }

                @Test
                internal fun `It should pass down values from api`() {
                    assertTrue(flowOutput.size == 2)
                    assertTrue(flowOutput[1].size == 2)
                    assertEquals(flowOutput[1][0], Country("BiH", "BA"))
                    assertEquals(flowOutput[1][1], Country("USA", "US"))
                }

                @Test
                internal fun `It should pass down values from cache`() {
                    assertTrue(flowOutput.size == 2)
                    assertTrue(flowOutput[0].isEmpty())
                }

                @Test
                internal fun `It should save countries to dao`() {
                    verify { mockCountriesDao.insertCountries(apiCountries) }
                }
            }
        }

        @Nested
        @DisplayName("Given pre cached data")
        inner class PreCachedData {
            val cacheCountries = arrayListOf(Country("BiH", "BA"))

            @BeforeEach
            internal fun setUp() {
                coEvery { mockCountriesDao.countries() } returns cacheCountries
                coEvery { mockCountriesDao.insertCountries(any()) } returns Unit
            }

            @Nested
            @DisplayName("When all countries are requested")
            inner class OnAllCountriesCall {
                lateinit var flowOutput: List<List<Country>>
                @BeforeEach
                internal fun setUp() = runBlocking {
                    flowOutput = countriesRepository.all().toList()
                }

                @Test
                internal fun `It should pass down values from api`() {
                    assertTrue(flowOutput.size == 2)
                    assertTrue(flowOutput[1].size == 2)
                    assertEquals(flowOutput[1][0], Country("BiH", "BA"))
                    assertEquals(flowOutput[1][1], Country("USA", "US"))
                }

                @Test
                internal fun `It should pass down values from cache`() {
                    assertTrue(flowOutput.size == 2)
                    assertTrue(flowOutput[0].size == 1)
                    assertEquals(flowOutput[0][0], Country("BiH", "BA"))
                }

                @Test
                internal fun `It should save countries to dao`() {
                    verify { mockCountriesDao.insertCountries(apiCountries) }
                }
            }
        }
    }

    @Nested
    @DisplayName("Given failing CountriesApi")
    inner class FailingApi {

        @BeforeEach
        internal fun setUp() {
            coEvery { mockCountriesApi.all() } throws IOException("Failed")
        }

        @Nested
        @DisplayName("Given empty cache")
        inner class EmptyCache {
            @BeforeEach
            internal fun setUp() {
                coEvery { mockCountriesDao.countries() } throws NoSuchElementException()
                coEvery { mockCountriesDao.insertCountries(any()) } returns Unit
            }

            @Nested
            @DisplayName("When all countries are requested")
            inner class OnAllCountriesCall {
                lateinit var flowOutput: MutableList<List<Country>>
                lateinit var error: Exception
                @BeforeEach
                internal fun setUp() = runBlocking {
                    flowOutput = arrayListOf()
                    try {
                        countriesRepository.all().collect {
                            flowOutput.add(it)
                        }
                    } catch (e: Exception) {
                        error = e
                    }
                }

                @Test
                internal fun `It should pass down error from api`() {
                    assertTrue(flowOutput.size == 1)
                    assertTrue(error is IOException)
                }

                @Test
                internal fun `It should pass down values from cache`() {
                    assertTrue(flowOutput.size == 1)
                    assertTrue(flowOutput[0].isEmpty())
                }
            }
        }

        @Nested
        @DisplayName("Given pre cached data")
        inner class PreCachedData {
            val cacheCountries = arrayListOf(Country("BiH", "BA"))

            @BeforeEach
            internal fun setUp() {
                coEvery { mockCountriesDao.countries() } returns cacheCountries
                coEvery { mockCountriesDao.insertCountries(any()) } returns Unit
            }

            @Nested
            @DisplayName("When all countries are requested")
            inner class OnAllCountriesCall {
                lateinit var flowOutput: MutableList<List<Country>>
                lateinit var error: Exception
                @BeforeEach
                internal fun setUp() = runBlocking {
                    flowOutput = arrayListOf()
                    try {
                        countriesRepository.all().collect {
                            flowOutput.add(it)
                        }
                    } catch (e: Exception) {
                        error = e
                    }
                }

                @Test
                internal fun `It should pass down error from api`() {
                    assertTrue(flowOutput.size == 1)
                    assertTrue(error is IOException)
                }

                @Test
                internal fun `It should pass down values from cache`() {
                    assertTrue(flowOutput.size == 1)
                    assertTrue(flowOutput[0].size == 1)
                    assertEquals(flowOutput[0][0], Country("BiH", "BA"))
                }
            }
        }
    }
}
