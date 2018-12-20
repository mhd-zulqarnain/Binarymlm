package com.redcodetechnologies.mlm.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiToken {

    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null
    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null
    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int? = null
    @SerializedName("userName")
    @Expose
    var userName: String? = null
    @SerializedName(".issued")
    @Expose
    var issued: String? = null
    @SerializedName(".expires")
    @Expose
    var expires: String? = null

}



