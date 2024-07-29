package com.noah.springdata.jdbc.repository.exception

class MyDuplicateKeyException : MyDatabaseException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}
