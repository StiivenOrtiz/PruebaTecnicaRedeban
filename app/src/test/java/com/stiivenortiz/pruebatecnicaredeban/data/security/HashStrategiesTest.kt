package com.stiivenortiz.pruebatecnicaredeban.data.security

import org.junit.Assert.assertEquals
import org.junit.Test

class HashStrategiesTest {

    private val sha256Strategy = Sha256HashStrategy()
    private val sha3Strategy = Sha3HashStrategy()

    @Test
    fun `Sha256HashStrategy should return correct hex string`() {
        // GIVEN
        val input = "test"
        val expected = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"

        // WHEN
        val result = sha256Strategy.execute(input)

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `Sha3HashStrategy should return correct hex string`() {
        // GIVEN
        val input = "test"
        val expected = "36f028580bb02cc8272a9a020f4200e346e276ae664e45ee80745574e2f5ab80"

        // WHEN
        val result = sha3Strategy.execute(input)

        // THEN
        assertEquals(expected, result)
    }
}