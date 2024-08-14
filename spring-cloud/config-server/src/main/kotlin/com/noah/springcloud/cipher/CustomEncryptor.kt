package com.noah.springcloud.cipher

import org.springframework.security.crypto.encrypt.TextEncryptor

class CustomEncryptor : TextEncryptor {
    override fun encrypt(text: String?): String {
        return text?.reversed()!!
    }

    override fun decrypt(encryptedText: String?): String {
        return encryptedText?.reversed()!!
    }
}
