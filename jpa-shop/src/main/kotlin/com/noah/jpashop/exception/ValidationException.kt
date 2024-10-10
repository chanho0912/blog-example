package com.noah.jpashop.exception

abstract class ValidationException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(
    message, cause
)

class DuplicateMemberException(
    message: String,
    cause: Throwable? = null
) : ValidationException(message, cause)