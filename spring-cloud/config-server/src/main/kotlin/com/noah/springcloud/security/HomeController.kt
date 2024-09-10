package com.noah.springcloud.security

import com.noah.springcloud.jwt.JwtTokenIssueService
import mu.KotlinLogging
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class HomeController(
    private val tokenIssueService: JwtTokenIssueService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("token")
    fun token(authentication: Authentication): String {
        logger.info { "Token requested for user ${authentication.name}" }
        val token = tokenIssueService.issueToken(authentication)
        logger.info { "Token issued for user ${authentication.name} token: $token" }
        return token
    }

    @GetMapping("home")
    fun home(principal: Principal): String {
        return "Welcome to the home page ${principal.name}"
    }

    @GetMapping("ignore")
    fun noAuth(): String {
        return "Welcome to the ignore page"
    }

    @GetMapping("permit-all")
    fun permitAll(): String {
        return "Welcome to the permit-all page"
    }

    @GetMapping("user")
    fun user(principal: Principal): String {
        return "Welcome to the user page ${principal.name}"
    }

    @GetMapping("admin")
    fun admin(principal: Principal): String {
        return "Welcome to the admin page ${principal.name}"
    }
}