package com.redcodetechnologies.mlm.models

import android.media.Image

data class Advertisement(var AdvertisementId: String? = null,
                         var AdvertisementName: String? = null,
                         var AdvertisementDescription: String? = null,
                         var AdvertisementImage: String? = null,
                         var IsActive:String? = null,
                         var CreateDate:String? = null
                )