package com.grimolizzi.kotlinspringexample.services

import com.grimolizzi.kotlinspringexample.utils.MongoContainer
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import com.grimolizzi.kotlinspringexample.model.Metadata as Metadata
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest
@Testcontainers
class MetadataServiceTests {

    @Autowired
    lateinit var mockService: MetadataService

    companion object {

        @Container
        val container = MongoContainer()

        @JvmStatic
        @DynamicPropertySource
        fun mongoDbProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.port", container::getFirstMappedPort)
        }

        init {
            container.start()
        }
    }

    @Test
    fun `Should store and retrieve metadata to Mongo`() {

        mockService.save(Metadata("ciao", "file/txt"))

        var list: List<Metadata> = mockService.findAll()

        assertEquals(list.size, 1)

        mockService.deleteAll()

        list = mockService.findAll()

        assertEquals(list.size, 0)

        container.stop()
    }
}