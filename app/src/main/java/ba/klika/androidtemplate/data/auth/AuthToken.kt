package ba.klika.androidtemplate.data.auth

import java.util.Calendar
import java.util.TimeZone

/**
 * Interface representing a simple authentication token that can be added to calls
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface AuthToken {
    val stringValue: String
}

/**
 * Basic implementation of [AuthToken]
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
open class BasicAuthToken(override val stringValue: String) : AuthToken

/**
 * Token that can be refreshed
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface RefreshableAuthToken : AuthToken {
    val refreshTokenStringValue: String
}

/**
 * Token that can expire
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface ExpirableAuthToken : AuthToken {
    fun expired(): Boolean
}

/**
 * Token that expires at a specific time
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface SetTimeExpirableAuthToken : ExpirableAuthToken {
    val expirationTimestamp: Long

    override fun expired(): Boolean {
        val currentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000
        return currentTime >= expirationTimestamp
    }
}

/**
 * Token that can be refreshed and expires at a set time (like JWT and OAuth2)
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface RefreshableSetTimeExpirableAuthToken : SetTimeExpirableAuthToken, RefreshableAuthToken
