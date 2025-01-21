package com.noah.entity

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table


@Entity
@Table(name = "authorization")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "authorization_type")
abstract class AuthorizationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,
    private val registeredClientId: String,
    private val principalName: String,

    private val authorizationGrantType: String,

    @Column(length = 1000)
    private val authorizedScopes: String,

    @Column(length = 4000)
    private val attributes: String,

    @Column(length = 500)
    private val state: String,

//    @Column(length = 4000)
//    private val refreshTokenValue: String = "",
//    private val refreshTokenIssuedAt: ZonedDateTime,
//    private val refreshTokenExpiresAt: ZonedDateTime,
//
//    @Column(length = 2000)
//    private val refreshTokenMetadata: String

)
