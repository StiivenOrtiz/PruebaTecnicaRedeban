package com.stiivenortiz.pruebatecnicaredeban.data.security

import java.security.MessageDigest
import javax.inject.Inject

class Sha3HashStrategy @Inject constructor() : HashStrategy {

    override fun execute(input: String): String {
        val digest = MessageDigest.getInstance("SHA3-256")
        val bytes = digest.digest(input.toByteArray(Charsets.UTF_8))

        return bytes.joinToString("") { "%02x".format(it) }
    }

}