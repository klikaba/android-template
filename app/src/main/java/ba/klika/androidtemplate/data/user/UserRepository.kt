package ba.klika.androidtemplate.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface UserRepository {
    fun get(id: Int): Flowable<User>
    fun create(registrationInfo: RegistrationInfo): Single<User>
}

@Singleton
class ApiUserRepository
@Inject constructor(
    private val userApi: UserApi,
    private val usersDao: UsersDao
) : UserRepository {
    override fun get(id: Int): Flowable<User> {
        return usersDao.user(id)
                // For now, return fake data when cache is empty
                .onErrorReturnItem(User(0, "", Date(), Date(), "no-role"))
                .concatWith { userApi.user(id) }
    }

    override fun create(registrationInfo: RegistrationInfo): Single<User> {
        return userApi.create(UserCreationRequest(registrationInfo)).doOnSuccess(usersDao::insertUser)
    }
}

interface UserApi {
    @POST("api/v1/users")
    fun create(@Body userCreationRequest: UserCreationRequest): Single<User>

    @GET("api/v1/users/{id}")
    fun user(@Path("id") id: Int): Single<User>
}

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    fun user(id: Int): Single<User>
}

data class UserCreationRequest(val user: RegistrationInfo)
