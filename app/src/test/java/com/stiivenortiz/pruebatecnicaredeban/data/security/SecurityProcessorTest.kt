package com.stiivenortiz.pruebatecnicaredeban.data.security

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SecurityProcessorTest {

    private val defaultStrategy: HashStrategy = mockk()
    private val alternativeStrategy: HashStrategy = mockk()
    private lateinit var securityProcessor: SecurityProcessor

    @Before
    fun setup() {
        securityProcessor = SecurityProcessor(defaultStrategy)
    }

    @Test
    fun `generateHash should use default strategy when no other is provided`() {
        // GIVEN
        val rawPan = "1234567890123456"
        val expectedHash = "7a51d064a1a216a692f753fcdab276e4ff201a01d8b66f56d50d4d719fd0dc87"
        every { defaultStrategy.execute(rawPan) } returns expectedHash

        // WHEN
        val result = securityProcessor.generateHash(rawPan)

        // THEN
        assertEquals(expectedHash, result)
        verify(exactly = 1) { defaultStrategy.execute(rawPan) }
        verify(exactly = 0) { alternativeStrategy.execute(any()) }
    }

    @Test
    fun `generateHash should use alternative strategy when provided`() {
        // GIVEN
        val rawPan = "1234567890123456"
        val expectedHash = "3fe93da886732fd563ba71f136f10dffc6a8955f911b36064b9e01b32f8af709"
        every { alternativeStrategy.execute(rawPan) } returns expectedHash

        // WHEN
        val result = securityProcessor.generateHash(rawPan, alternativeStrategy)

        // THEN
        assertEquals(expectedHash, result)
        verify(exactly = 1) { alternativeStrategy.execute(rawPan) }
        verify(exactly = 0) { defaultStrategy.execute(any()) }
    }
}