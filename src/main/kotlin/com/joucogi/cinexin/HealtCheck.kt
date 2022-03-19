package com.joucogi.cinexin

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckGetController {
    @GetMapping("/health-check")
    fun index() = ResponseEntity.ok().body(
        hashMapOf(
            "status" to "ok",
            "application" to "cinexin-backoffice"
        )
    )
}