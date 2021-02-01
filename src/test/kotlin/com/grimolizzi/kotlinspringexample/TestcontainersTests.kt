package com.grimolizzi.kotlinspringexample

import com.grimolizzi.kotlinspringexample.utils.MinioContainer
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@SpringBootTest
@Testcontainers
class TestcontainersTests {

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
            mockService.store(getMockedFile())
            println("...file stored.")

            val retrievedFile = mockService.retrieve("file.txt")

            val fileContents = retrievedFile.bufferedReader().use { it.readText() }

            assertEquals(fileContents, "Hello, World!")
        }
    }

    fun getMockedFile(): MultipartFile {
        val path: Path = Paths.get("src/test/resources/file.txt")
        val name = "file.txt"
        val originalFileName = "file.txt"
        val contentType = "text/plain"
        val fileContent: ByteArray = Files.readAllBytes(path)
        return MockMultipartFile(name, originalFileName, contentType, fileContent)
    }
}