package com.grimolizzi.kotlinspringexample

import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.net.URLConnection
import javax.servlet.http.HttpServletResponse

@RestController
class MainController(
    val minioService: MinioService,
    val metadataRepository: MetadataRepository
) {
    @GetMapping("/{filename}")
    fun retrieve(@PathVariable("filename") filename: String, response: HttpServletResponse) {
        val inputStream: InputStream = minioService.retrieve(filename)

        // Set the content type and attachment header
        response.addHeader("Content-disposition", "attachment;filename=$filename")
        response.contentType = URLConnection.guessContentTypeFromName(filename)

        // Copy the stream to the response's output stream
        IOUtils.copy(inputStream, response.outputStream)
        response.flushBuffer()
    }

    @PostMapping("/")
    fun store(@RequestParam("file") file: MultipartFile) =
        minioService.store(file)

    @GetMapping("/metadata")
    fun getMetadata(): MutableList<Metadata> = metadataRepository.findAll()

    @PostMapping("/metadata")
    fun postMetadata() {
        val metadata = Metadata("Mosconi.pdf", "image/pdf")
        metadataRepository.save(metadata)
    }
}