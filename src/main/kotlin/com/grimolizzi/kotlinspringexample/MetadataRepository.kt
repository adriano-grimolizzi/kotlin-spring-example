package com.grimolizzi.kotlinspringexample

import com.grimolizzi.kotlinspringexample.model.Metadata
import org.springframework.data.mongodb.repository.MongoRepository

interface MetadataRepository: MongoRepository<Metadata, String> {
}
