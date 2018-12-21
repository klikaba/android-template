package ba.klika.androidtemplate.data.country

import ba.klika.androidtemplate.testhelpers.rxGroup
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.IOException

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class ApiCountriesRepositorySpek : Spek({
    describe("api countries repository implementation") {
        context("working api and empty cache") {
            val countriesApi by memoized { Mockito.mock(CountriesApi::class.java) }
            val countriesDao by memoized { Mockito.mock(CountriesDao::class.java) }
            val countriesRepository by memoized {
                ApiCountriesRepository(countriesApi, countriesDao)
            }

            val countries = ArrayList<Country>().also {
                it.add(Country("BiH", "BA"))
            }

            beforeEach {
                given(countriesApi.all()).willReturn(Single.just(CountriesResponse(countries)))
                given(countriesDao.countries()).willReturn(Single.error(NoSuchElementException()))
            }

            rxGroup("all countries call") {
                lateinit var countriesObserver: TestSubscriber<List<Country>>
                beforeEach {
                    val countriesCall = countriesRepository.all()
                    countriesObserver = countriesCall.test()
                    countriesObserver.awaitTerminalEvent()
                }

                it("should pass down countries from api") {
                    countriesObserver.assertComplete()
                            .assertValueCount(2)
                            .assertValueAt(1) { it.size == 1 && it[0].let { country -> country.name == "BiH" && country.code == "BA" } }
                    Mockito.verify(countriesApi).all()
                }

                it("should pass down countries from cache") {
                    countriesObserver.assertComplete()
                            .assertValueCount(2)
                            .assertValueAt(0) { it.isEmpty() }
                    Mockito.verify(countriesDao).countries()
                }

                it("should save countries to dao") {
                    Mockito.verify(countriesDao).insertCountries(countries)
                }
            }
        }

        context("working api and cached data") {
            val countriesApi by memoized { Mockito.mock(CountriesApi::class.java) }
            val countriesDao by memoized { Mockito.mock(CountriesDao::class.java) }
            val countriesRepository by memoized {
                ApiCountriesRepository(countriesApi, countriesDao)
            }

            val apiCountries = ArrayList<Country>().also {
                it.add(Country("BiH", "BA"))
                it.add(Country("USA", "US"))
            }
            val cacheCountries = ArrayList<Country>().also {
                it.add(Country("BiH", "BA"))
            }

            beforeEach {
                given(countriesApi.all()).willReturn(Single.just(CountriesResponse(apiCountries)))
                given(countriesDao.countries()).willReturn(Single.just(cacheCountries))
            }

            rxGroup("all countries call") {
                lateinit var countriesObserver: TestSubscriber<List<Country>>
                beforeEach {
                    val countriesCall = countriesRepository.all()
                    countriesObserver = countriesCall.test()
                    countriesObserver.awaitTerminalEvent()
                }

                it("should pass down countries from api") {
                    countriesObserver.assertComplete()
                            .assertValueCount(2)
                            .assertValueAt(1) {
                                it.size == 2
                                        && it[0].let { country -> country.name == "BiH" && country.code == "BA" }
                                        && it[1].let { country -> country.name == "USA" && country.code == "US" }
                            }
                    Mockito.verify(countriesApi).all()
                }

                it("should pass down countries from cache") {
                    countriesObserver.assertComplete()
                            .assertValueCount(2)
                            .assertValueAt(0) {
                                it.size == 1
                                        && it[0].let { country -> country.name == "BiH" && country.code == "BA" }
                            }
                    Mockito.verify(countriesDao).countries()
                }

                it("should save countries to dao") {
                    Mockito.verify(countriesDao).insertCountries(apiCountries)
                }
            }
        }
        context("failing api and empty cache") {
            val countriesApi by memoized { Mockito.mock(CountriesApi::class.java) }
            val countriesDao by memoized { Mockito.mock(CountriesDao::class.java) }
            val countriesRepository by memoized {
                ApiCountriesRepository(countriesApi, countriesDao)
            }

            beforeEach {
                given(countriesApi.all()).willReturn(Single.error(IOException()))
                given(countriesDao.countries()).willReturn(Single.error(NoSuchElementException()))
            }

            rxGroup("all countries call") {
                lateinit var countriesObserver: TestSubscriber<List<Country>>
                beforeEach {
                    val countriesCall = countriesRepository.all()
                    countriesObserver = countriesCall.test()
                    countriesObserver.awaitTerminalEvent()
                }

                it("should pass down error from api") {
                    countriesObserver.assertError(IOException::class.java)
                    Mockito.verify(countriesApi).all()
                }

                it("should pass down countries from cache") {
                    countriesObserver
                            .assertValueCount(1)
                            .assertValueAt(0) {
                                it.isEmpty()
                            }
                    Mockito.verify(countriesDao).countries()
                }
            }
        }
        context("failing api and cached data") {
            val countriesApi by memoized { Mockito.mock(CountriesApi::class.java) }
            val countriesDao by memoized { Mockito.mock(CountriesDao::class.java) }
            val countriesRepository by memoized {
                ApiCountriesRepository(countriesApi, countriesDao)
            }

            val cacheCountries = ArrayList<Country>().also {
                it.add(Country("BiH", "BA"))
            }

            beforeEach {
                given(countriesApi.all()).willReturn(Single.error(IOException()))
                given(countriesDao.countries()).willReturn(Single.just(cacheCountries))
            }

            rxGroup("all countries call") {
                lateinit var countriesObserver: TestSubscriber<List<Country>>
                beforeEach {
                    val countriesCall = countriesRepository.all()
                    countriesObserver = countriesCall.test()
                    countriesObserver.awaitTerminalEvent()
                }

                it("should pass down error from api") {
                    countriesObserver.assertError(IOException::class.java)
                    Mockito.verify(countriesApi).all()
                }

                it("should pass down countries from cache") {
                    countriesObserver
                            .assertValueCount(1)
                            .assertValueAt(0) {
                                it.size == 1
                                        && it[0].let { country -> country.name == "BiH" && country.code == "BA" }
                            }
                    Mockito.verify(countriesDao).countries()
                }
            }
        }
    }
})