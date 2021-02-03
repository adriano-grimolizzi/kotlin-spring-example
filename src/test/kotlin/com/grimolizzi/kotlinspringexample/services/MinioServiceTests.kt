package com.grimolizzi.kotlinspringexample.services

import com.grimolizzi.kotlinspringexample.utils.FileUtils
import com.grimolizzi.kotlinspringexample.utils.MinioContainer
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class MinioServiceTests {

    private val accessKey = "AKIAIOSFODNN7EXAMPLE"
    private val secretKey = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY"

    @Autowired
    lateinit var mockService: MinioService

    @Test
    fun `Should store and retrieve files to MinIO`() {

        MinioContainer(
            MinioContainer.CredentialsProvider(accessKey, secretKey)
        ).use { it ->
            it.start()
            mockService.changePort(it.firstMappedPort) // Can't access port 9000...

            println("Storing file...")
            mockService.store(FileUtils.getMockMultipartFile())
            println("...file stored.")

            val retrievedFile = mockService.retrieve("file.txt")

            val fileContents = retrievedFile.bufferedReader().use { it.readText() }

            assertEquals(fileContents, "Hello, World!")
            it.stop()
        }
    }
}