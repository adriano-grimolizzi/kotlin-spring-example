package com.grimolizzi.kotlinspringexample

import com.grimolizzi.kotlinspringexample.properties.MinioProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(MinioProperties::class)
class KotlinSpringExampleApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringExampleApplication>(*args)
}
