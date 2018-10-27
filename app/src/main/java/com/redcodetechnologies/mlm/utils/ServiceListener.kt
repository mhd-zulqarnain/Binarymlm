package com.redcodetechnologies.mlm.utils


interface ServiceListener<T> {

    fun success(obj: T)
    fun fail(error: ServiceError)
}