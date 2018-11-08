package ba.klika.androidtemplate.data.user

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Entity
data class User(
        @field:PrimaryKey
        @field:NonNull
        val id: Int,
        val email: String,
        @field:SerializedName("created_at")
        val createdAt: Date,
        @field:SerializedName("updated_at")
        val updatedAt: Date,
        val role: String)

data class RegistrationInfo(val email: String, val password: String)
