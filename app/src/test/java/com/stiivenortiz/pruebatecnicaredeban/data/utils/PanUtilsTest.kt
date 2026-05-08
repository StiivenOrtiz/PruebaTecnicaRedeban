package com.stiivenortiz.pruebatecnicaredeban.data.utils

import org.junit.Assert.*
import org.junit.Test

class PanUtilsTest {

    @Test
    fun `extractPan should return everything before the D character`() {
        // GIVEN:
        val track2 = "1234567890123456D251210100000"

        // WHEN
        val result = PanUtils.extractPan(track2)

        // THEN
        assertEquals("1234567890123456", result)
    }

    @Test
    fun `extractPan should return original text if D is not present`() {
        // GIVEN
        val rawPan = "1234567890123456"

        // WHEN
        val result = PanUtils.extractPan(rawPan)

        // THEN
        assertEquals("1234567890123456", result)
    }

    @Test
    fun `maskPan should format 16 digits card correctly`() {
        // GIVEN
        val rawPan = "1234567890123456"

        // WHEN
        val result = PanUtils.maskPan(rawPan)

        // THEN
        assertEquals("1234 56** **** 3456", result)
    }

    @Test
    fun `maskPan should return original text if length is 10 or less`() {
        // GIVEN
        val shortPan = "1234567890"

        // WHEN
        val result = PanUtils.maskPan(shortPan)

        // THEN
        assertEquals("1234567890", result)
    }
}