package ba.klika.androidtemplate.data.country

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Entity
data class Country(
    val name: String,
    @field:PrimaryKey
    @field:NonNull
    val code: String
)
