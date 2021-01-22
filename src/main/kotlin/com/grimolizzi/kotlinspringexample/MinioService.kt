package com.grimolizzi.kotlinspringexample

import com.grimolizzi.kotlinspringexample.properties.MinioProperties
import io.minio.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.io.BufferedInputStream
import java.lang.Exception
import java.nio.file.Path

@Service
class MinioService(private val properties: MinioProperties) {

    val minioClient: MinioClient = MinioClient.builder()
        .endpoint(properties.endpoint)
        .credentials(properties.accessKey, properties.secretKey)
        .build()

    fun retrieve(filename: String): InputStream =
        minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(properties.bucketName)
                .`object`(filename)
                .build()
        )

    fun store(file: MultipartFile) {
        val inputStream: InputStream = BufferedInputStream(file.inputStream)

        val bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.bucketName).build())
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.bucketName).build())
        }

        minioClient.putObject(
            PutObjectArgs
                .builder()
                .bucket(properties.bucketName)
                .`object`(file.originalFilename)
                .stream(inputStream, file.size, -1)
                .contentType(file.contentType)
                .build()
        )
    }

    fun getFile() = Metadata("MarioMagnotta.jpg", "image/jpeg")
}