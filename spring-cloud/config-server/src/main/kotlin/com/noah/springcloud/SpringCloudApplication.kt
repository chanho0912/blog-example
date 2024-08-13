package com.noah.springcloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

// http://host:port/<file-name>/<profile>
@EnableConfigServer
@SpringBootApplication
class SpringCloudApplication

fun main(args: Array<String>) {
    runApplication<SpringCloudApplication>(*args)
}
