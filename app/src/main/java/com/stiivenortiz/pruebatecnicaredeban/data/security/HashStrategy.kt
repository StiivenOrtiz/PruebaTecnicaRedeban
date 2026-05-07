package com.stiivenortiz.pruebatecnicaredeban.data.security

interface HashStrategy {

    fun execute(input: String): String

}