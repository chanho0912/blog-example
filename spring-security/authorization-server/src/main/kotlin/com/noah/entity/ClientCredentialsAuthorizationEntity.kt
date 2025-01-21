package com.noah.entity

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.ZonedDateTime

@Entity
@DiscriminatorValue(value = "CLIENT_CREDENTIALS")
class ClientCredentialsAuthorizationEntity(
    registeredClientId: String,
    principalName: String,
    authorizedScopes: String,
    attributes: String,
    state: String,

    @Column(length = 4000)
    private val accessTokenValue: String,
    private val accessTokenIssuedAt: ZonedDateTime,
    private val accessTokenExpiresAt: ZonedDateTime,

    @Column(length = 2000)
    private val accessTokenMetadata: String,
    private val accessTokenType: String,

    @Column(length = 1000)
    private val accessTokenScopes: String

) : AuthorizationEntity(
    registeredClientId = registeredClientId,
    principalName = principalName,
    authorizedScopes = authorizedScopes,
    attributes = attributes,
    authorizationGrantType = "client_credentials",
    state = state
)
