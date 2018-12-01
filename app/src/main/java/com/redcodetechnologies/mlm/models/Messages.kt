package com.redcodetechnologies.mlm.models

data class Messages(var  Id :Int? = null,
                    var  Sender :String? = null,
                    var  Sender_Name :String? = null,
                    var  UserId :Int? = null,
                    var  SponserId :Int? = null,
                    var  Message :String? = null,
                    var  IsRead :Boolean? = null,
                    var  CreateDate :String? = null,
                    var  MessageImage :String? = null
                    )

