package ba.klika.androidtemplate.data.country

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface CountriesRepository {
    fun all(): Flowable<List<Country>>
}

@Singleton
class ApiCountriesRepository
@Inject constructor(
        private val countriesApi: CountriesApi,
        private val countriesDao: CountriesDao) : CountriesRepository {
    override fun all(): Flowable<List<Country>> {
        return countriesDao.countries().onErrorReturnItem(ArrayList())
                .concatWith {
                    countriesApi.all().map { res -> res.countries }
                }
    }
}

interface CountriesApi {
    @GET("api/v1/countries")
    fun all(): Single<CountriesResponse>
}

@Dao
interface CountriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<Country>)

    @Query("SELECT * FROM country")
    fun countries(): Single<List<Country>>
}

data class CountriesResponse(val countries: List<Country>)

