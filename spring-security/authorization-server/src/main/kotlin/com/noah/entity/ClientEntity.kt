package com.noah.entity

import com.noah.common.ClientUtils
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.util.StringUtils
import java.time.Duration
import java.time.ZonedDateTime


@Entity
@Table(name = "clients")
class ClientEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0L,

    private val clientId: String,

    private val clientSecret: String,

    private val clientName: String,

    private val clientAuthenticationMethods: String,

    private val authorizationGrantTypes: String,

    private val scopes: String,

    /**
     * requireAuthorizationConsent
     *
     * 역할: 클라이언트가 사용자의 동의를 요구하는지 여부를 설정.
     * 사용법:
     * true: 사용자 동의 화면을 표시합니다. (주로 Authorization Code Grant에서 사용)
     * false: 사용자 동의를 생략합니다. (신뢰할 수 있는 클라이언트일 때 사용)
     * 주의:
     * 동의를 생략하면 보안 위험이 증가할 수 있으므로 신중하게 설정해야 합니다.
     * requireProofKey
     *
     * 역할: PKCE (Proof Key for Code Exchange) 사용 여부를 설정.
     * 사용법:
     * true: PKCE를 강제합니다. (권장, 특히 Public 클라이언트인 경우)
     * false: PKCE를 사용하지 않습니다.
     * 주의:
     * Public 클라이언트(Android, iOS 앱 등)에는 반드시 true로 설정해야 합니다.
     * tokenEndpointAuthenticationMethods
     *
     * 역할: 클라이언트가 인증 서버의 토큰 엔드포인트를 호출할 때 사용할 인증 방법.
     * 예시 값:
     * ClientAuthenticationMethod.CLIENT_SECRET_BASIC: Basic 인증 방식.
     * ClientAuthenticationMethod.CLIENT_SECRET_POST: 클라이언트 ID와 시크릿을 POST 요청 본문에 포함.
     * ClientAuthenticationMethod.NONE: 인증을 생략. (주로 Public 클라이언트에서 사용)
     * 주의:
     * 적절한 인증 방법을 설정하지 않으면 토큰 요청이 실패할 수 있습니다.
     * jwkSetUrl
     *
     * 역할: 클라이언트의 JSON Web Key Set(JWKS) URL을 설정.
     * 사용법: 클라이언트가 공개 키를 제공하는 경우 설정.
     * 주의:
     * 이 값은 인증 서버가 클라이언트의 서명을 검증하는 데 필요할 수 있습니다.
     *
     */
    @Embedded
    private val clientSetting: ClientSetting,

    /**
     * accessTokenTimeToLive
     *
     * 역할: Access Token의 유효 기간.
     * 예시 값: Duration.ofMinutes(30) (기본값: 5분)
     * 주의:
     * 짧게 설정하면 보안은 강화되지만 사용자 경험에 영향을 줄 수 있습니다.
     * refreshTokenTimeToLive
     *
     * 역할: Refresh Token의 유효 기간.
     * 예시 값: Duration.ofDays(30)
     * 주의:
     * 너무 길게 설정하면 보안 위험이 증가할 수 있습니다.
     * reuseRefreshTokens
     *
     * 역할: Refresh Token의 재사용 가능 여부.
     * 사용법:
     * true: Refresh Token 재사용 허용.
     * false: Refresh Token을 한 번 사용하면 폐기하고 새로 발급.
     * 주의:
     * false로 설정하면 보안은 강화되지만 클라이언트의 구현이 복잡해질 수 있습니다.
     * idTokenSignatureAlgorithm
     *
     * 역할: OIDC ID Token의 서명 알고리즘을 정의.
     * 예시 값:
     * SignatureAlgorithm.RS256: RSA 서명 알고리즘.
     * SignatureAlgorithm.ES256: ECDSA 서명 알고리즘.
     * 주의:
     * 클라이언트가 지원하는 알고리즘과 일치해야 합니다.
     * accessTokenFormat
     *
     * 역할: Access Token의 포맷을 설정.
     * 예시 값:
     * OAuth2TokenFormat.SELF_CONTAINED: JWT 토큰으로 발급.
     * OAuth2TokenFormat.REFERENCE: 레퍼런스 토큰(서버에서 상태를 유지).
     * 주의:
     * JWT는 분산 시스템에서 효율적이지만 토큰 철회(revocation)가 어렵습니다. 레퍼런스 토큰은 서버에서 상태를 관리하므로 철회가 용이합니다.
     * authorizationCodeTimeToLive
     *
     * 역할: Authorization Code의 유효 기간.
     * 예시 값: Duration.ofMinutes(5) (기본값)
     * 주의:
     * 너무 길게 설정하면 Authorization Code가 도난당할 위험이 증가합니다.
     * refreshTokenRotationEnabled (Spring Authorization Server 최신 버전에서 사용 가능)
     *
     * 역할: Refresh Token을 사용할 때마다 새로 발급되도록 설정.
     * 사용법:
     * true: Rotation 활성화.
     * false: Rotation 비활성화.
     * 주의:
     * Rotation을 활성화하면 보안성이 증가하지만, 클라이언트는 새 토큰을 저장해야 하므로 구현이 복잡해질 수 있습니다.
     */
    @Embedded
    private val tokenSetting: TokenSetting
) {
    fun asRegisteredClient(): RegisteredClient {
        return RegisteredClient.withId(id.toString())
            .clientId(clientId)
            .clientSecret(clientSecret)
            .clientName(clientName)
            .clientAuthenticationMethods { methods ->
                StringUtils.commaDelimitedListToSet(clientAuthenticationMethods).forEach {
                    methods.add(ClientUtils.resolveClientAuthenticationMethods(it))
                }
            }
            .authorizationGrantTypes { types ->
                StringUtils.commaDelimitedListToSet(authorizationGrantTypes).forEach {
                    types.add(ClientUtils.resolveAuthorizationGrantType(it))
                }
            }
            .scopes { scopes ->
                StringUtils.commaDelimitedListToSet(this.scopes).forEach {
                    scopes.add(it)
                }
            }
            .clientSettings(clientSetting.toSpringClientSettings())
            .tokenSettings(tokenSetting.toSpringTokenSettings())
            .build()
    }

}

@Embeddable
class ClientSetting(
    private val requireProofKey: Boolean = false,
    private val requireAuthorizationConsent: Boolean = false,
) {
    fun toSpringClientSettings(): ClientSettings {
        return ClientSettings.builder()
            .requireProofKey(requireProofKey)
            .requireAuthorizationConsent(requireAuthorizationConsent)
            .build()
    }
}

@Embeddable
class TokenSetting(
    private val accessTokenTimeToLive: Duration = Duration.ofDays(60),
    private val refreshTokenTimeToLive: Duration = Duration.ofDays(365),
    private val reuseRefreshTokens: Boolean,
) {
    fun toSpringTokenSettings(): TokenSettings {
        return TokenSettings.builder()
            .accessTokenTimeToLive(accessTokenTimeToLive)
            .refreshTokenTimeToLive(refreshTokenTimeToLive)
            .reuseRefreshTokens(reuseRefreshTokens)
            .build()
    }
}
