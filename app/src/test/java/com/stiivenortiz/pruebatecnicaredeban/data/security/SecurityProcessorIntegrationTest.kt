package com.stiivenortiz.pruebatecnicaredeban.data.security

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class SecurityProcessorIntegrationTest {
    private val sha256Strategy = Sha256HashStrategy()
    private val sha3Strategy = Sha3HashStrategy()

    private val securityProcessor = SecurityProcessor(sha256Strategy)

    @Test
    fun `generateHash with real SHA-256 strategy should return valid hash`() {
        // GIVEN
        val rawPan = "1234567890123456"
        val expected = "7a51d064a1a216a692f753fcdab276e4ff201a01d8b66f56d50d4d719fd0dc87"

        // WHEN
        val result = securityProcessor.generateHash(rawPan)

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `generateHash with real SHA3 strategy passed as argument should return valid hash`() {
        // GIVEN
        val rawPan = "1234567890123456"
        val expected = "3fe93da886732fd563ba71f136f10dffc6a8955f911b36064b9e01b32f8af709"

        // WHEN
        val result = securityProcessor.generateHash(rawPan, sha3Strategy)

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `verify different strategies produce different hashes for same input`() {
        // GIVEN
        val rawPan = "450799XXXXXX0123"

        // WHEN
        val hash256 = securityProcessor.generateHash(rawPan)
        val hash3 = securityProcessor.generateHash(rawPan, sha3Strategy)

        // THEN
        assertNotEquals("Hashes must not be the same between different algorithms", hash256, hash3)
        assertEquals(64, hash256.length)
        assertEquals(64, hash3.length)
    }
}