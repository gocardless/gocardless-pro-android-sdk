package com.gocardless.gocardlesssdk.util

import java.io.InputStreamReader
import java.io.Reader

object TestFileManager {
    fun read(path: String): String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        val content = reader.readText()
        reader.close()
        return content
    }

    fun reader(path: String): Reader =
        InputStreamReader(javaClass.classLoader?.getResourceAsStream(path))
}