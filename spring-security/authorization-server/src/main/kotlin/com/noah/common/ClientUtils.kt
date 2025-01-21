package com.noah.common

import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

object ClientUtils {
    fun resolveClientAuthenticationMethods(clientAuthenticationMethod: String): ClientAuthenticationMethod =
        when (clientAuthenticationMethod) {
            ClientAuthenticationMethod.CLIENT_SECRET_BASIC.value -> ClientAuthenticationMethod.CLIENT_SECRET_BASIC
            ClientAuthenticationMethod.CLIENT_SECRET_POST.value -> ClientAuthenticationMethod.CLIENT_SECRET_POST
            ClientAuthenticationMethod.PRIVATE_KEY_JWT.value -> ClientAuthenticationMethod.PRIVATE_KEY_JWT
            ClientAuthenticationMethod.CLIENT_SECRET_JWT.value -> ClientAuthenticationMethod.CLIENT_SECRET_JWT
            else -> throw IllegalArgumentException("Unknown client authentication method: $clientAuthenticationMethod")
        }

    fun resolveAuthorizationGrantType(authorizationGrantType: String): AuthorizationGrantType =
        when (authorizationGrantType) {
            AuthorizationGrantType.AUTHORIZATION_CODE.value -> AuthorizationGrantType.AUTHORIZATION_CODE
            AuthorizationGrantType.CLIENT_CREDENTIALS.value -> AuthorizationGrantType.CLIENT_CREDENTIALS
            AuthorizationGrantType.REFRESH_TOKEN.value -> AuthorizationGrantType.REFRESH_TOKEN
            else -> AuthorizationGrantType(authorizationGrantType) // Custom authorization grant type
        }

}
