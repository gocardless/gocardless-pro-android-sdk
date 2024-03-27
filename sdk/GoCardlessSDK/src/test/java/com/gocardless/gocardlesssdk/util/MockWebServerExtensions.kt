package com.gocardless.gocardlesssdk.util

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import java.net.HttpURLConnection

fun MockWebServer.successResponse(path: String) {
    val content = TestFileManager.read(path)

    this.enqueue(
        MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(content)
    )
}

fun MockWebServer.errorResponse(path: String) {
    val content = TestFileManager.read(path)

    this.enqueue(
        MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(content)
    )
}

fun MockWebServer.assertRequest(filePath: String, url: String) {
    val recordedRequest = takeRequest()
    val responseBody = recordedRequest.body.readUtf8()
    val content = TestFileManager.read(filePath)

    // Then
    Assert.assertEquals(url, recordedRequest.path)
    Assert.assertEquals(content, responseBody)
}