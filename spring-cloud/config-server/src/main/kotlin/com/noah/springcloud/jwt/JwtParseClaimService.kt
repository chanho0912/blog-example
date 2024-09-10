//package com.noah.springcloud.jwt
//
//import io.jsonwebtoken.Claims
//import io.jsonwebtoken.Jwts
//import org.springframework.stereotype.Service
//import java.util.Date
//
//@Service
//class JwtParseClaimService {
//    fun <T> parseClaims(
//        jwtToken: String, transform: (Claims) -> T?
//    ): T? {
//        val claims = parseClaims(jwtToken)
//        return transform(claims)
//    }
//
//    fun parseSubject(jwt: String): String? {
//        return parseClaims(jwt) { claim ->
//            claim.subject
//        }
//    }
//
//    fun parseExpiration(jwt: String): Date? {
//        return parseClaims(jwt) { claim ->
//            claim.expiration
//        }
//    }
//
//    private fun parseClaims(jwtToken: String): Claims {
//        return Jwts.parser()
//            .verifyWith(KeyProvider.getSignInKey())
//            .build()
//            .parseSignedClaims(jwtToken)
//            .payload
//    }
//}
//
//data class JwtClaim(
//    val username: String,
//    val password: String
//)