package com.noah

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * The roles of an Authorization Server:
 * - Authenticates clients by verifying Client ID and Client Secret (credentials)
 * - Issues access tokens after successfully authenticating clients
 *
 * Spring Authorization Server uses NimbusJwtEncoder to encode and sign issued JWT tokens.
 * By default, access tokens are signed using an RSA key pair in memory
 */
@SpringBootApplication
class AuthorizationServerApplication

fun main(args: Array<String>) {
    runApplication<AuthorizationServerApplication>(*args)
}
