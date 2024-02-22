package com.gocardless.gocardlesssdk.model

enum class Environment(val baseUrl: String) {
    Live("https://api.gocardless.com"),
    Sandbox("https://api-sandbox.gocardless.com");
}