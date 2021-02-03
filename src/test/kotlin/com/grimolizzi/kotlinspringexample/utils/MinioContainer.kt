package com.grimolizzi.kotlinspringexample.utils

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy
import java.time.Duration

class MinioContainer(image: String?, credentials: CredentialsProvider?) :
    GenericContainer<MinioContainer?>(image ?: "$DEFAULT_IMAGE:$DEFAULT_TAG") {

    constructor(credentials: CredentialsProvider?) : this("$DEFAULT_IMAGE:$DEFAULT_TAG", credentials)

    companion object {
        private const val DEFAULT_PORT = 9000
        private const val DEFAULT_IMAGE = "minio/minio"
        private const val DEFAULT_TAG = "edge"
        private const val MINIO_ACCESS_KEY = "MINIO_ACCESS_KEY"
        private const val MINIO_SECRET_KEY = "MINIO_SECRET_KEY"
        private const val DEFAULT_STORAGE_DIRECTORY = "/data"
        private const val HEALTH_ENDPOINT = "/minio/health/ready"
    }

    class CredentialsProvider(val accessKey: String, val secretKey: String)

    init {
        addExposedPort(DEFAULT_PORT)
        if (credentials != null) {
            withEnv(MINIO_ACCESS_KEY, credentials.accessKey)
            withEnv(MINIO_SECRET_KEY, credentials.secretKey)
        }
        withCommand("server", DEFAULT_STORAGE_DIRECTORY)
        setWaitStrategy(
            HttpWaitStrategy()
                .forPort(DEFAULT_PORT)
                .forPath(HEALTH_ENDPOINT)
                .withStartupTimeout(Duration.ofMinutes(2))
        )
    }
}