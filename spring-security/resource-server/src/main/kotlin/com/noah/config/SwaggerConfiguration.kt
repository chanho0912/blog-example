package com.noah.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.OAuthFlow
import io.swagger.v3.oas.models.security.OAuthFlows
import io.swagger.v3.oas.models.security.Scopes
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun customizeOpenApi(): OpenAPI {
        val authScheme = "bearerAuth"

        return OpenAPI().apply {
            components = Components().addSecuritySchemes(authScheme, oauthScheme())
        }.addSecurityItem(
            SecurityRequirement().addList(authScheme)
        )
    }

    private fun oauthScheme(): SecurityScheme {
        return SecurityScheme().apply {
            type = SecurityScheme.Type.OAUTH2
            flows = oauthFlows()
        }
    }

    private fun oauthFlows(): OAuthFlows {
        val oauthFlow = OAuthFlow().apply {
            tokenUrl = "http://localhost:20080/oauth2/token"
            scopes = Scopes().apply {
                addString("read", "Read access")
            }
        }

        return OAuthFlows().apply {
            clientCredentials = oauthFlow
        }
    }
}
