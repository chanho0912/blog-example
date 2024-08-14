package com.noah.springcloud

import com.noah.springcloud.cipher.CustomEncryptor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.cloud.config.server.encryption.CipherEnvironmentEncryptor
import org.springframework.cloud.config.server.encryption.TextEncryptorLocator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

// http://host:port/<file-name>/<profile>
@EnableConfigServer
@SpringBootApplication
class SpringCloudApplication {
    @Bean
    @Primary
    fun textEncryptorLocator(): TextEncryptorLocator {
        return TextEncryptorLocator { CustomEncryptor() }
    }

    @Bean
    @Primary
    fun cipherEnvironmentEncryptor(textEncryptorLocator: TextEncryptorLocator): CipherEnvironmentEncryptor {
        return CipherEnvironmentEncryptor(textEncryptorLocator)
    }
}

fun main(args: Array<String>) {
    runApplication<SpringCloudApplication>(*args)
}
