package com.stiivenortiz.pruebatecnicaredeban.data.security

import com.stiivenortiz.pruebatecnicaredeban.data.security.annotations.Sha256
import javax.inject.Inject

class SecurityProcessor @Inject constructor(
    @param:Sha256 private val hashStrategy: HashStrategy
) {

    fun generateHash(rawPan: String, strategy: HashStrategy = hashStrategy): String =
        strategy.execute(rawPan)

}