package com.grimolizzi.kotlinspringexample.services

import com.grimolizzi.kotlinspringexample.MetadataRepository
import com.grimolizzi.kotlinspringexample.model.Metadata as Metadata
import org.springframework.stereotype.Service

@Service
class MetadataService(
    var repository: MetadataRepository
) {
    fun save(metadata: Metadata): Metadata = repository.save(metadata)
    fun findAll(): List<Metadata> = repository.findAll()
    fun deleteAll() = repository.deleteAll()
}