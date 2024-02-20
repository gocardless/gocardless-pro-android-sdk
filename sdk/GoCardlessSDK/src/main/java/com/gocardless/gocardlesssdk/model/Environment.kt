package com.gocardless.gocardlesssdk.model

public enum class Environment(val baseUrl: String) {
    Live("https://api.gocardless.com"),
    Sandbox("https://api-sandbox.gocardless.com");
}