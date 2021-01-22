package com.grimolizzi.kotlinspringexample.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("minio")
class MinioProperties {
    val endpoint = "http://localhost:9000/"
    val accessKey = "AKIAIOSFODNN7EXAMPLE"
    val secretKey = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"
    val bucketName = "bucket1"
}
