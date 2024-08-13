package com.noah.springcloud.sample

import com.noah.springcloud.sample.SimpleRestController.DataBaseProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.core.env.Environment
import org.springframework.core.env.getProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableConfigurationProperties(DataBaseProperty::class)
class SimpleRestController(
    private val dataBaseProperty: DataBaseProperty,
    private val environment: Environment
) {

    @ConfigurationProperties("db")
    data class DataBaseProperty(
        val url: String,
        val username: String,
        val password: String
    )

    @GetMapping("db")
    fun getDbProperty(): DataBaseProperty = dataBaseProperty

    @GetMapping("envdetails")
    fun envDetails(): String = environment.toString()

    @GetMapping("env/db")
    fun getDb(): String {
        return environment.getProperty<String>("db.url").toString()
    }

}
