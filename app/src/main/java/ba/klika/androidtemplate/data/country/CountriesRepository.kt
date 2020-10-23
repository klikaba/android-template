package ba.klika.androidtemplate.data.country

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface CountriesRepository {
    fun all(): Flow<List<Country>>
}

@Singleton
class ApiCountriesRepository
@Inject constructor(
    private val countriesApi: CountriesApi,
    private val countriesDao: CountriesDao
) : CountriesRepository {
    override fun all(): Flow<List<Country>> = flow {
        countriesDao.runCatching { countries() }
                .onSuccess {
                    emit(it)
                }.onFailure {
                    emit(listOf<Country>())
                }
        emit(countriesApi.all().countries.also { countriesDao.insertCountries(it) })
    }
}

interface CountriesApi {
    @GET("api/v1/countries")
    suspend fun all(): CountriesResponse
}

@Dao
interface CountriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<Country>)

    @Query("SELECT * FROM country")
    suspend fun countries(): List<Country>
}

data class CountriesResponse(val countries: List<Country>)
