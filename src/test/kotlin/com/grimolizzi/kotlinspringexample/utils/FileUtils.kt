package com.grimolizzi.kotlinspringexample.utils

import org.springframework.mock.web.MockMultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class FileUtils {
    companion object {
        fun getMockMultipartFile(): MockMultipartFile {
            val path: Path = Paths.get("src/test/resources/file.txt")
            val name = "file" // has to be the same as the value of RequestParam in the controller
            val originalFileName = "file.txt"
            val contentType = "text/plain"
            val fileContent: ByteArray = Files.readAllBytes(path)
            return MockMultipartFile(name, originalFileName, contentType, fileContent)
        }
    }
}