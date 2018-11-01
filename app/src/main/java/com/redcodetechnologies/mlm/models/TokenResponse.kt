package com.redcodetechnologies.mlm.models

data class TokenResponse(var access_token: String? = null,
                         var token_type: String? = null,
                         var expires_in: String? = null,
                         var userName: String? = null

)