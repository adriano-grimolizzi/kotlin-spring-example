package com.grimolizzi.kotlinspringexample

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(MainController::class)
class MainControllerTests {

    @MockkBean
    lateinit var mockService: MainService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Should return Metadata`() {
        val expectedMetadata = Metadata("MarioMagnotta.jpeg", "image/jpeg")
        val objectMapper = ObjectMapper()
        val expectedMetadataJson = objectMapper.writeValueAsString(expectedMetadata)

        every { mockService.getFile() } returns expectedMetadata

        performMockMvc()
            .andExpect(status().isOk)
            .andExpect(content().json(expectedMetadataJson))
            .andReturn()

        verify { mockService.getFile() }
    }

    private fun performMockMvc() =
        mockMvc.perform(MockMvcRequestBuilders
            .get("/metadata")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
}