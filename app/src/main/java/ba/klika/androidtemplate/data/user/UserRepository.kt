package ba.klika.androidtemplate.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    fun get(id: Int): Flow<User>
    suspend fun create(registrationInfo: RegistrationInfo): User
}

@Singleton
class ApiUserRepository
@Inject constructor(
    private val userApi: UserApi,
    private val usersDao: UsersDao
) : UserRepository {
    override fun get(id: Int): Flow<User> = flow {
        usersDao.runCatching { user(id) }
                .onSuccess {
                    emit(it)
                }.onFailure {
                    emit(User(0, "", Date(), Date(), "no-role"))
                }
        emit(userApi.user(id).also { usersDao.insertUser(it) })
    }

    override suspend fun create(registrationInfo: RegistrationInfo): User =
            userApi.create(UserCreationRequest(registrationInfo)).also {
               usersDao.insertUser(it)
            }
}

interface UserApi {
    @POST("api/v1/users")
    suspend fun create(@Body userCreationRequest: UserCreationRequest): User

    @GET("api/v1/users/{id}")
    suspend fun user(@Path("id") id: Int): User
}

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun user(id: Int): User
}

data class UserCreationRequest(val user: RegistrationInfo)
