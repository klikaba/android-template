package ba.klika.androidtemplate.data.auth.oauth2.request

import com.google.gson.annotations.SerializedName

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class OAuth2GrantRequest(@field:SerializedName("grant_type")
                                  private val grantType: String,
                                  @field:SerializedName("client_id")
                                  private val clientId: String,
                                  @field:SerializedName("client_secret")
                                  private val clientSecret: String)
