package com.renattele.sma1.data

import java.security.MessageDigest

object HashUtil {
    fun hash(data: String): String = sha256(data)

    private fun sha256(data: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(data.toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}

infix fun String.hashMatches(other: String): Boolean {
    return HashUtil.hash(this) == other
}