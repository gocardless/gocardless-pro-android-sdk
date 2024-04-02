package com.gocardless.gocardlesssdk.network

enum class Environment(val baseUrl: String) {
    Live("https://api.gocardless.com"),
    Sandbox("https://api-sandbox.gocardless.com");
}