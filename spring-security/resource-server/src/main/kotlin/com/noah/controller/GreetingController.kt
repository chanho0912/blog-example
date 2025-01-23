package com.noah.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class GreetingController {

    @GetMapping
    fun index(@AuthenticationPrincipal jwt: Jwt): String {
        return "Hello, ${jwt.subject}!"
    }

    @GetMapping("/greeting")
    fun greet(): String {
        return "Hello, World!"
    }
}
