package com.grimolizzi.kotlinspringexample.utils

import org.testcontainers.containers.GenericContainer

class MongoContainer : GenericContainer<MongoContainer?>("$DEFAULT_IMAGE:$DEFAULT_TAG") {

    companion object {
        private const val DEFAULT_PORT = 27017
        private const val DEFAULT_IMAGE = "mongo"
        private const val DEFAULT_TAG = "4.4.2"
    }

    init {
        addExposedPort(DEFAULT_PORT)
    }
}