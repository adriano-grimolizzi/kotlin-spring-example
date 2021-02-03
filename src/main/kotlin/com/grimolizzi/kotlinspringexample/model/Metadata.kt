package com.grimolizzi.kotlinspringexample.model

import org.springframework.data.annotation.Id

class Metadata {
    @Id
    var id: String? = null
    var fileName: String? = null
    var contentType: String? = null

    constructor() {}
    constructor(fileName: String?, contentType: String?) {
        this.fileName = fileName
        this.contentType = contentType
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Metadata) return false
        return fileName == other.fileName && contentType == other.contentType
    }
}