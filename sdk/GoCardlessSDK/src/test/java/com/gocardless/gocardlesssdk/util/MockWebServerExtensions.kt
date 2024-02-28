package com.gocardless.gocardlesssdk.util

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
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
