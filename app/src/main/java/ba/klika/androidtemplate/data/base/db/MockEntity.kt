package ba.klika.androidtemplate.data.base.db

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Entity
class MockEntity {

    @PrimaryKey
    @NonNull
    var id: String? = null
}