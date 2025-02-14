package com.noah.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import com.noah.repository.ClientRepository
import com.noah.repository.JPARegisteredClientAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*


@Configuration
class OAuth2AuthorizationServerConfiguration {

    @Bean
    fun filterChain(
        http: HttpSecurity,
        jwtGenerator: JwtGenerator,
        settings: AuthorizationServerSettings,
        registeredClientRepository: RegisteredClientRepository,
    ): SecurityFilterChain {
        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer()

        return http
//            .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
            .with(authorizationServerConfigurer) { configurer ->
                configurer.registeredClientRepository(registeredClientRepository)
                    .tokenGenerator(jwtGenerator)
                    .authorizationServerSettings(settings)

            }
            .cors { cors ->
                cors.configurationSource {
                    val source = CorsConfiguration()
                    source.apply {
                        allowedOrigins = listOf("http://localhost:20081")
                        allowedMethods = listOf("GET", "POST", "OPTIONS")
                        allowedHeaders = listOf("*")
                        allowCredentials = true
                    }
                    source
                }
            }
            .csrf { csrf ->
                csrf.disable()
            }
            .httpBasic { httpBasic ->
                httpBasic.disable()
            }
            .formLogin { formLogin ->
                formLogin.disable()
            }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(HttpMethod.OPTIONS, "/oauth2/token").permitAll()
                auth.anyRequest().authenticated()
            }
            .build()
    }

    @Bean
    fun jwtTokenGenerator(jwtEncoder: JwtEncoder): JwtGenerator {
        val generator = JwtGenerator(jwtEncoder)
        generator.setJwtCustomizer { context ->
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                val registeredClient = context.registeredClient
                context.claims.claims { claim ->
                    claim.put("client_name", registeredClient.clientName)
                }
            }
        }
        return generator
    }

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val keyPair: KeyPair = generateRsaKey()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey
        val rsaKey: RSAKey = RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }

    private fun generateRsaKey(): KeyPair =
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            keyPairGenerator.generateKeyPair()
        } catch (ex: Exception) {
            throw IllegalStateException(ex)
        }

    @Bean
    fun registeredClientRepository(
        clientRepository: ClientRepository,
        passwordEncoder: PasswordEncoder
    ): RegisteredClientRepository =
        JPARegisteredClientAdapter(clientRepository, passwordEncoder)

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder =
        OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)

    @Bean
    fun jwtEncoder(jwkSource: JWKSource<SecurityContext>): JwtEncoder =
        NimbusJwtEncoder(jwkSource)
}
