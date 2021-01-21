package com.grimolizzi.kotlinspringexample

import org.springframework.stereotype.Service

@Service
class MainService {

    fun getFile(): Metadata {
        return Metadata("MarioMagnotta.jpg", "image/jpeg")
    }
}