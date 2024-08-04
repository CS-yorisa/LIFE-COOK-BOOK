package com.cookbook.life.security

import org.springframework.security.crypto.password.PasswordEncoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class passwordEncoder {
    fun encode(rawPassword: CharSequence): String {
        return hashedSha512(rawPassword.toString())
    }

    fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        val hashedPassword = encode(rawPassword)
        return encodedPassword == hashedPassword
    }

    private fun hashedSha512(input: String): String {
        val result = StringBuilder()
        try {
            val md: MessageDigest = MessageDigest.getInstance("SHA-512")
            val digested: ByteArray = md.digest(input.toByteArray())
            for (i in digested.indices) {
                result.append(Integer.toHexString(0xFF and digested[i].toInt()))
            }
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
        return result.toString()
    }
}

