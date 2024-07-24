package com.noah.springdata.blog.serviceprovider

import io.kotest.core.spec.style.StringSpec
import mu.KotlinLogging
import java.util.ServiceLoader

class ServiceLoaderTest : StringSpec({
    val logger = KotlinLogging.logger {}

    "service-provider loading mechanism" {

        val loadedImplementation =
            ServiceLoader.load(SampleInterface::class.java)

        val iter = loadedImplementation.iterator()

        while (iter.hasNext()) {
            logger.info { iter.next()::class.java.simpleName }
        }
    }
})
