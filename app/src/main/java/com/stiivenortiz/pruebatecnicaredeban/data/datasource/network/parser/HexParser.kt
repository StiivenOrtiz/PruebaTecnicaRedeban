package com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.parser

object HexParser {

    fun hexToAscii(hex: String): String {
        val cleanHex = hex.trim()

        val byteArray = ByteArray(cleanHex.length / 2)

        for (i in cleanHex.indices step 2) {
            val byte = cleanHex.substring(i, i + 2).toInt(16)
            byteArray[i / 2] = byte.toByte()
        }

        return String(byteArray, Charsets.US_ASCII)
    }

}