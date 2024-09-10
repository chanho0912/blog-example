package com.noah.springcloud.config

import org.springframework.cloud.config.client.ConfigClientProperties
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.web.client.RestTemplate


//@Configuration
//class ConfigServiceBootstrapConfig(
//    private val environment: ConfigurableEnvironment
//) {

//    @Bean
//    fun configServicePropertySourceLocator(
//        configClientProperties: ConfigClientProperties
//    ): ConfigServicePropertySourceLocator {
//        val configServicePropertySourceLocator = ConfigServicePropertySourceLocator(configClientProperties)
//        configServicePropertySourceLocator.setRestTemplate(platformConfigServerRestTemplate(configClientProperties))
//        return configServicePropertySourceLocator
//    }
//
//    @Bean
//    fun platformConfigServerRestTemplate(
//        configClientProperties: ConfigClientProperties
//    ): RestTemplate {
//
//    }
//}