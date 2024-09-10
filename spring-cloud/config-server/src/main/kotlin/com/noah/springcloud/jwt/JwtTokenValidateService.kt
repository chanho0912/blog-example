//package com.noah.springcloud.jwt
//
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.stereotype.Service
//import java.util.*
//
//@Service
//class JwtTokenValidateService(
//    private val jwtParseClaimService: JwtParseClaimService
//) {
//
//    fun validateToken(jwtToken: String, user: UserDetails): Boolean {
//        val valid = (jwtParseClaimService.parseExpiration(jwtToken)
//            ?.before(Date())
//            ?: false)
//        val username = jwtParseClaimService.parseSubject(jwtToken)
//        return username == user.username && valid
//    }
//}