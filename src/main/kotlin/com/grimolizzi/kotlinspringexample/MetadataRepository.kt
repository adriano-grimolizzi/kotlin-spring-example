package com.grimolizzi.kotlinspringexample

import org.springframework.data.mongodb.repository.MongoRepository

interface MetadataRepository: MongoRepository<Metadata, String> {
}
