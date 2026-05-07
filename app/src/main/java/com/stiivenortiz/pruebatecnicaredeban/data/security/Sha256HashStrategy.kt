package com.stiivenortiz.pruebatecnicaredeban.data.security

import java.security.MessageDigest
import javax.inject.Inject

class Sha256HashStrategy @Inject constructor() : HashStrategy {

    override fun execute(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest(input.toByteArray(Charsets.UTF_8))

        return bytes.joinToString("") { "%02x".format(it) }
    }

}