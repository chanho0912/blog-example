package com.noah.springcloud.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.noah.springcloud.jwt.KeyProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun user(): InMemoryUserDetailsManager {
        val user1 =
            User.withUsername("user1")
                .password("{noop}1234")
                .roles("USER")
                .build()
        val user2 =
            User.withUsername("user2")
                .password("{noop}1234")
                .roles("ADMIN")
                .build()

        return InMemoryUserDetailsManager(
            user1, user2
        )
    }

    @Bean
    fun configure(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers("/ignore")
        }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf ->
                csrf.disable()
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/ignore").permitAll()  // /ignore는 인증 필요 없음
                    .requestMatchers("/permit-all").permitAll()  // /permit-all은 인증 필요 없음
                    .requestMatchers("/token").permitAll()
                    .requestMatchers("/user").hasRole("USER")  // /user는 ROLE_USER만 접근 가능
                    .requestMatchers("/admin").hasRole("ADMIN")  // /admin은 ROLE_ADMIN만 접근 가능
                    .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt(Customizer.withDefaults())
            }
            .exceptionHandling { ex ->
                ex
                    .authenticationEntryPoint { _, _, _ ->
                        BearerTokenAuthenticationEntryPoint()
                    }
                    .accessDeniedHandler { _, _, _ ->
                        BearerTokenAccessDeniedHandler()
                    }
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .httpBasic(Customizer.withDefaults())

        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withSecretKey(KeyProvider.getSignInKey()).build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = OctetSequenceKey.Builder(KeyProvider.getSignInKey()).build()
        return NimbusJwtEncoder(
            ImmutableJWKSet(
                JWKSet(jwk)
            )
        )
    }

}