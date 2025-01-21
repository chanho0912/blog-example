package com.noah.controller

import com.noah.common.ClientUtils
import com.noah.repository.JPARegisteredClientAdapter
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("api/v1/oauth2/clients")
class ClientRegistrationController(
    private val registeredClientRepository: JPARegisteredClientAdapter
) {

    @PostMapping
    fun registerClient(
        @RequestBody clientRegistration: ClientRegistration
    ): String {
        val clientId = registeredClientRepository.saveAndGetClientId(clientRegistration.toRegisteredClient())
        return "Client registered successfully. Client ID: $clientId"
    }
}

data class ClientRegistration(
    val clientSecret: String,
    val clientName: String,
    val clientAuthenticationMethods: List<String>,
    val authorizationGrantTypes: List<String>,
    val scopes: List<String>,
) {
    fun toRegisteredClient(): RegisteredClient {
        return RegisteredClient.withId("0")
            .clientId(UUID.randomUUID().toString())
            .clientSecret(clientSecret)
            .clientName(clientName)
            .clientAuthenticationMethods { authMethods ->
                clientAuthenticationMethods.map { method ->
                    ClientUtils.resolveClientAuthenticationMethods(method)
                }.let(authMethods::addAll)
            }
            .authorizationGrantTypes { grantTypes ->
                authorizationGrantTypes.map { type ->
                    ClientUtils.resolveAuthorizationGrantType(type)
                }.let(grantTypes::addAll)
            }
            .scopes { clientScopes -> clientScopes.addAll(scopes) }
            .build()
    }
}
