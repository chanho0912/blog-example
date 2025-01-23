package com.noah.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/resource")
class BasicResourceController {

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_read')")
    fun getResources() = "Some Resources ..."

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    fun createResource() = "Resource created successfully..."
}
