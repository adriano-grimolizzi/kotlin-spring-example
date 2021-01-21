package com.grimolizzi.kotlinspringexample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController(val service: MainService) {

    @GetMapping("/metadata")
    fun getMetadata(): Metadata {
        return service.getFile()
    }
}