
package com.redcodetechnologies.mlm.utils



class ServiceError {
    var errorObject: Throwable? = null
    var message: String? = null

    constructor() {
        message = ""
    }

    constructor(message: String?) {
        this.message = message
    }

    constructor(errorObject: Throwable?, message: String?) {
        this.errorObject = errorObject
        this.message = message
    }

}