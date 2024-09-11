package com.noah.springcloud.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class JwtTokenIssueService(
    private val jwtEncoder: JwtEncoder
) {

    fun issueToken(
        authentication: Authentication
    ): String {
        val now = Instant.now()
        val scope =
            authentication.authorities.map { grantedAuthority ->
                grantedAuthority.authority // USER
            }
                .joinToString()

        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(10, ChronoUnit.MINUTES))
            .subject(authentication.name)
            // 어딘가에 role을 넣어야함.
            .claim("scope", scope)
            .build()

        val headers = JwsHeader.with(MacAlgorithm.HS256).build()

        return jwtEncoder.encode(
            JwtEncoderParameters.from(
                headers,
                claims
            )
        )
            .tokenValue

//        return Jwts.builder()
//            .claims()
//            .add(extraClaims).and()
//            .subject(user.username)
//            .issuedAt(Date(System.currentTimeMillis()))
//            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
//            .signWith(KeyProvider.getSignInKey(), Jwts.SIG.HS256)
//            .compact()
    }

}