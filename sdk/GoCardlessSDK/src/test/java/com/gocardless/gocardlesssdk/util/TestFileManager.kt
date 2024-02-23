package com.gocardless.gocardlesssdk.util

import java.io.InputStreamReader

object TestFileManager {
    fun read(path: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        val content = reader.readText()
        reader.close()
        return content
    }
}