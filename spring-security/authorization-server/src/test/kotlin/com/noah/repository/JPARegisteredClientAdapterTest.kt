package com.noah.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.Instant

@Transactional
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JPARegisteredClientAdapterTest(
    private val jpaRegisteredClientAdapter: JPARegisteredClientAdapter
) {

    @Test
    fun saveNewClient() {
        val now = Instant.now()
        val registeredClient =
            RegisteredClient.withId("0")
                .clientId("client-id")
                .clientIdIssuedAt(now)
                .clientName("client-name")
                .clientSecret("client-secret")
                .clientSecretExpiresAt(now.plusSeconds(100))
                .scope("read")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientSettings(ClientSettings.builder().requireProofKey(false).build())
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofDays(1)).build())
                .build()

        // Save the client
        jpaRegisteredClientAdapter.save(registeredClient)

        // Find the client
        val foundClient = jpaRegisteredClientAdapter.findByClientId("client-id")

        // Assert the client
        assertThat(foundClient != null)
        assertThat(foundClient?.clientId).isEqualTo("client-id")
        assertThat(foundClient?.clientName).isEqualTo("client-name")
        assertThat(foundClient?.clientSecret).isEqualTo("client-secret")
        assertThat(foundClient?.clientSecretExpiresAt).isEqualTo(now.plusSeconds(100))
        assertThat(foundClient?.clientAuthenticationMethods).containsExactly(ClientAuthenticationMethod.CLIENT_SECRET_JWT)
        assertThat(foundClient?.authorizationGrantTypes).containsExactly(AuthorizationGrantType.CLIENT_CREDENTIALS)
        assertThat(foundClient?.scopes).containsExactly("read")
        assertThat(foundClient?.clientSettings?.isRequireProofKey).isFalse
        assertThat(foundClient?.tokenSettings?.accessTokenTimeToLive).isEqualTo(Duration.ofDays(1))
    }
}
