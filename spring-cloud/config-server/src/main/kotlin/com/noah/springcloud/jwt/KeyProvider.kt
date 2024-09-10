package com.noah.springcloud.jwt

import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec


object KeyProvider {
    private val SECRET =
        "something-weird-secret-key256256256256256256256256256256256256256256256256256256256256256something-weird-secret-key256256256256256256256256256256256256256256256256256256256256256something-weird-secret-key256256256256256256256256256256256256256256256256256256256256256something-weird-secret-key256256256256256256256256256256256256256256256256256256256256256something-weird-secret-key256256256256256256256256256256256256256256256256256256256256256"

    fun getSignInKey(): SecretKey {
        return SecretKeySpec(SECRET.toByteArray(), "HmacSHA256")
    }
}