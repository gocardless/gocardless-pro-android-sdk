package com.gocardless.gocardlesssdk.error

import com.gocardless.gocardlesssdk.util.TestFileManager
import com.gocardless.gocardlesssdk.util.TestNetworkManager
import org.junit.Assert.assertEquals
import org.junit.Test

class ErrorMapperTest {
    private val errorMapper = ErrorMapper(TestNetworkManager.gson)

    @Test
    fun testAuthenticationError() {
        val reader = TestFileManager.reader("./billing_request/error.json")
        val exception = errorMapper.process(401, reader)
        assertEquals(AuthenticationError::class.java, exception::class.java)
    }

    @Test
    fun testPermissionError() {
        val reader = TestFileManager.reader("./billing_request/error.json")
        val exception = errorMapper.process(403, reader)
        assertEquals(PermissionError::class.java, exception::class.java)
    }

    @Test
    fun testRateLimitError() {
        val reader = TestFileManager.reader("./billing_request/error.json")
        val exception = errorMapper.process(429, reader)
        assertEquals(RateLimitError::class.java, exception::class.java)
    }

    @Test
    fun testMalformedResponseError() {
        val reader = TestFileManager.reader("./billing_request/error_malformed.json")
        val exception = errorMapper.process(500, reader)
        assertEquals(MalformedResponseError::class.java, exception::class.java)
    }

    @Test
    fun testInvalidApiUsageError() {
        val reader = TestFileManager.reader("./billing_request/invalid_api_usage.json")
        val exception = errorMapper.process(400, reader)
        assertEquals(InvalidApiUsageError::class.java, exception::class.java)
    }

    @Test
    fun testInvalidStateError() {
        val reader = TestFileManager.reader("./billing_request/invalid_state.json")
        val exception = errorMapper.process(422, reader)
        assertEquals(InvalidStateError::class.java, exception::class.java)
    }

    @Test
    fun testValidationFailedError() {
        val reader = TestFileManager.reader("./billing_request/validation_failed.json")
        val exception = errorMapper.process(400, reader)
        assertEquals(ValidationFailedError::class.java, exception::class.java)
    }

    @Test
    fun testGoCardlessInternalError() {
        val reader = TestFileManager.reader("./billing_request/gocardless_error.json")
        val exception = errorMapper.process(400, reader)
        assertEquals(GoCardlessInternalError::class.java, exception::class.java)
    }
}