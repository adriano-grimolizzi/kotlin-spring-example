package com.grimolizzi.kotlinspringexample

import com.grimolizzi.kotlinspringexample.properties.MinioProperties
import io.minio.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.io.BufferedInputStream

@Service
class MinioService(private val properties: MinioProperties) {

    // It's 'var' because of the function 'changePort'
    var minioClient: MinioClient = MinioClient.builder()
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

        if (!minioClient.bucketExists(getBucketExistsArgs())) {
            minioClient.makeBucket(getMakeBucketArgs())
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

    fun changePort(newPort: Int) {
        minioClient = MinioClient.builder()
            .endpoint("http://localhost:$newPort/")
            .credentials(properties.accessKey, properties.secretKey)
            .build()
    }
    fun getBucketExistsArgs(): BucketExistsArgs = BucketExistsArgs.builder().bucket(properties.bucketName).build()
    fun getMakeBucketArgs(): MakeBucketArgs = MakeBucketArgs.builder().bucket(properties.bucketName).build()

//    fun getFile() = Metadata("MarioMagnotta.jpg", "image/jpeg")
}