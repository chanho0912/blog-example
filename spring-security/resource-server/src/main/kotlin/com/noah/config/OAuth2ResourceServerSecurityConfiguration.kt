package com.noah.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import java.util.concurrent.TimeUnit


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class OAuth2ResourceServerSecurityConfiguration {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        // @formatter:off
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.GET, "/api/v1/**").hasAuthority("SCOPE_read")
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { resourceServer ->
                resourceServer.jwt { jwt ->
                    jwt.decoder(jwtDecoder(cacheManager()))
                }
            }
        // @formatter:on
        return http.build()
    }

    @Bean
    fun cacheManager(): CacheManager {
        val caffeine = Caffeine.newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .maximumSize(1000)

        val caffeineCacheManager = CaffeineCacheManager()
        caffeineCacheManager.setCaffeine(caffeine)

        return caffeineCacheManager

    }

    @Bean
    fun jwtDecoder(cacheManager: CacheManager): JwtDecoder {
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:20080/oauth2/jwks")
            .cache(cacheManager.getCache("jwkSetCache"))
            .build()
    }

//    @Bean
//    fun jwkSource(): JWKSource<SecurityContext> {
//        val jwtSetUrl = URL("http://localhost:20080/oauth2/jwks")
//        val ttl = (60 * 60 * 1000).toLong() // 1 hour
//        val refreshTimeout = (60 * 1000).toLong() // 1 minute
//        val outageTTL = (30 * 60 * 1000).toLong() // 30 minute
//
//        return JWKSourceBuilder.create<SecurityContext>(jwtSetUrl)
//            .cache(ttl, refreshTimeout)
//            .retrying(true)
//            .outageTolerant(outageTTL)
//            .build()
//    }

//    @Bean
//    fun verifiableJWKSetRetriever(): ResourceRetriever = object : DefaultResourceRetriever() {
//        private fun verifyJwt(jwt: SignedJWT) {
//            // Verify the JWT
//        }
//
//        override fun retrieveResource(url: URL): Resource {
//            val resource = super.retrieveResource(url)
//
//            return kotlin.runCatching {
//                SignedJWT.parse(resource.getContent())
//            }
//                .fold(
//                    onSuccess = { jwt ->
//                        verifyJwt(jwt)
//                        Resource(jwt.jwtClaimsSet.toString(), "application/json")
//                    },
//                    onFailure = { e ->
//                        throw IOException("Invalid JWT: ${e.message}", e)
//                    }
//                )
//        }
//    }

}
