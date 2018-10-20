package com.company.redcode.royalcryptoexchange.utils


interface ServiceListener<T> {

    fun success(obj: T)
    fun fail(error: ServiceError)
}