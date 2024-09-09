package com.noah.springcloud.security

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class HomeController {

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