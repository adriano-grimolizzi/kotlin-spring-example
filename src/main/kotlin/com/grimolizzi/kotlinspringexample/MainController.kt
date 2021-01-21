package com.grimolizzi.kotlinspringexample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {

    @GetMapping("/hello")
    fun sayHello() = "Hello World!"
}