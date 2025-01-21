package com.noah.repository

import com.noah.entity.ClientEntity
import com.noah.entity.ClientSetting
import com.noah.entity.TokenSetting
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.time.ZoneId
import java.time.ZonedDateTime


@Component
@Transactional
class JPARegisteredClientAdapter(
    private val clientRepository: ClientRepository,
    private val passwordEncoder: PasswordEncoder
) : RegisteredClientRepository {

    fun saveAndGetClientId(registeredClient: RegisteredClient): String {
        clientRepository.save(registeredClient.toEntity())
        return registeredClient.clientId
    }

    override fun save(registeredClient: RegisteredClient) {
        clientRepository.save(registeredClient.toEntity())
    }

    override fun findById(id: String): RegisteredClient? {
        return clientRepository.findById(id.toLong())
            .orElse(null)
            .asRegisteredClient()
    }

    override fun findByClientId(clientId: String): RegisteredClient? {
        return clientRepository.findByClientId(clientId)
            ?.asRegisteredClient()
    }

    private fun RegisteredClient.toEntity(): ClientEntity {
        val authenticationMethods = getClientAuthenticationMethods()
        val clientAuthenticationMethods: MutableList<String> = ArrayList(authenticationMethods.size)
        authenticationMethods.forEach { clientAuthenticationMethod ->
            clientAuthenticationMethods.add(
                clientAuthenticationMethod.getValue()
            )
        }

        val authorizationTypes = getAuthorizationGrantTypes()
        val authorizationGrantTypes: MutableList<String> = ArrayList(authorizationTypes.size)
        authorizationTypes.forEach { authorizationGrantType ->
            authorizationGrantTypes.add(
                authorizationGrantType.getValue()
            )
        }

        return ClientEntity(
            clientId = this.clientId,
            clientSecret = passwordEncoder.encode(requireNotNull(this.clientSecret)),
            clientName = this.clientName,
            clientAuthenticationMethods = StringUtils.collectionToCommaDelimitedString(clientAuthenticationMethods),
            authorizationGrantTypes = StringUtils.collectionToCommaDelimitedString(authorizationGrantTypes),
            scopes = StringUtils.collectionToCommaDelimitedString(this.scopes),
            clientSetting = ClientSetting(
                requireProofKey = this.clientSettings.isRequireProofKey,
                requireAuthorizationConsent = this.clientSettings.isRequireAuthorizationConsent,
            ),
            tokenSetting = TokenSetting(
                accessTokenTimeToLive = this.tokenSettings.accessTokenTimeToLive,
                refreshTokenTimeToLive = this.tokenSettings.refreshTokenTimeToLive,
                reuseRefreshTokens = true
            )
        )
    }
}
