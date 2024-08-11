package com.noah.simpleitemservice.repository

object JpaLikePatternUtils {
    private const val LIKE_MATCHER = "%"

    fun fullMatch(param: String): String =
        "$LIKE_MATCHER$param$LIKE_MATCHER"
}
