package com.grimolizzi.kotlinspringexample

import com.grimolizzi.kotlinspringexample.model.Metadata
import com.grimolizzi.kotlinspringexample.utils.FileUtils
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.justRun
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.InputStream

@ExtendWith(SpringExtension::class)
@WebMvcTest(MainController::class)
class MainControllerTests {

    @MockkBean
    lateinit var mockService: MinioService

    @MockkBean
    lateinit var mockMetadataRepository: MetadataRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Should handle store method`() {

        val mockMetadata = Metadata("file.txt", "text/plain")

        every { mockMetadataRepository.save(any()) } returns mockMetadata
        justRun { mockService.store(any()) }

        mockMvc.perform(
            MockMvcRequestBuilders.multipart("/")
                .file(FileUtils.getMockMultipartFile())
                .characterEncoding("UTF-8")
        )
            .andExpect(status().isOk)
            .andReturn()

        verify { mockMetadataRepository.save(any()) }
        verify { mockService.store(any()) }
    }

    @Test
    fun `should handle retrieve method`() {

        val mockedInputStream: InputStream = "WILSON IS DATING AMBER".byteInputStream()

        every { mockService.retrieve("file.txt") } returns mockedInputStream

        val mockMvcResponse = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/file.txt")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        verify { mockService.retrieve("file.txt") }

        assertEquals(mockMvcResponse.response.contentAsString, "WILSON IS DATING AMBER");
    }

    @Test
    fun `Should handle getMetadata method`() {

        val mockedList = listOf(Metadata("file.txt", "text/plain"))

        every { mockMetadataRepository.findAll() } returns mockedList

        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/metadata")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        verify { mockMetadataRepository.findAll() }
    }

    @Test
    fun `Should handle deleteAll (metadata) method`() {

        justRun { mockMetadataRepository.deleteAll() }

        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/metadata")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andReturn()

        verify { mockMetadataRepository.deleteAll() }
    }
}