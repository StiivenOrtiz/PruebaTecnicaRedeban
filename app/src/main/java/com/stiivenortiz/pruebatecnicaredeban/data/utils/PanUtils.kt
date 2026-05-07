package com.stiivenortiz.pruebatecnicaredeban.data.utils

object PanUtils {

    fun extractPan(text: String): String {
        return text.substringBefore('D')
    }

    fun maskPan(rawPan: String): String {
        if (rawPan.length <= 10) return rawPan

        return "${rawPan.take(4)} ${rawPan.substring(4, 6)}** **** ${rawPan.takeLast(4)}"
    }

}