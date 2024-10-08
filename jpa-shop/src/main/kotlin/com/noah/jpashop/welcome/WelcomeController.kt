package com.noah.jpashop.welcome

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WelcomeController {

    @GetMapping("hello")
    fun hello(model: Model): String {
        model.addAttribute("data", "hello!!")
        return "hello"
    }
}