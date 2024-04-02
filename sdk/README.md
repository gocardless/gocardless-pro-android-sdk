# GoCardless SDK Example App

The GoCardless Android SDK is a tool that enables developers to integrate GoCardless payments into their Android applications. 
To help developers get started, a sample app has been created that demonstrates how to use the SDK. 
The app provides a clear and practical example of how to implement GoCardless payments within an Android app.

## Getting started

```xml
<dependency>
    <groupId>com.gocardless</groupId>
    <artifactId>gocardlesssdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

With Gradle:

```
implementation 'com.gocardless:gocardlesssdk:1.0.0'
```

## Initializing the client

The client is initialised with an access token, and is configured to use GoCardless' live environment by default:

```kotlin
import com.gocardless.gocardlesssdk.GoCardlessSDK
import com.gocardless.gocardlesssdk.network.Environment

GoCardlessSDK.initSDK(
    "YOUR_ACCESS_TOKEN",
    Environment.Live
)
```

## Examples

Note: To be able to make any request, you must initialise the SDK.

### Fetching List of Billing Requests

Please also make sure that all the requests are made in a correct `Scope` before launching.

```kotlin
viewModelScope.launch {
    val response = GoCardlessSDK.billingRequestService.listBillingRequests()
}
```

A response can either be a `ApiSuccess` or `ApiError`

- `ApiSuccess.value` contains the actual result from the operation
- `ApiError.error` contains the actual error from the operation

```kotlin
when(response) {
    is ApiSuccess -> {
        _uiState.value = MainUiState.Success(response.value)
    }
    is ApiError -> {
        _uiState.value = MainUiState.Error("Couldn't fetch")
    }
}
```


### Handling Errors

Whenever the API encounters an issue, it returns a `GoCardlessError` or its derivatives to provide more context about the error. Below are the types of errors you may encounter:

- `AuthenticationError`: Indicates an issue with authentication.
- `GoCardlessInternalError`: Denotes an internal error within the GoCardless system.
- `InvalidApiUsageError`: Occurs when the API is used incorrectly.
- `InvalidStateError`: Indicates an invalid state in the system.
- `MalformedResponseError`: Denotes an issue with the response received from the API.
- `PermissionError`: Occurs when the user does not have the necessary permissions.
- `RateLimitError`: Indicates that the rate limit for API requests has been exceeded.
- `ValidationFailedError`: Denotes a validation failure, usually with user input.


## Compatibility
### Language: Kotlin
Our SDK is built using Kotlin, a modern programming language for Android development.

### Target SDK: 34 (Android 14) - Latest
Our SDK is optimized for the latest Android features, providing compatibility up to SDK version 34.

### Minimum SDK: 28 (Android 9)
Our SDK supports a wide range of devices, ensuring compatibility with Android 9 (API level 28) and higher. We have achieved a coverage of 95% on devices with this minimum SDK version.

### Libraries
Our SDK utilizes the following libraries to enhance its functionality:

- OkHttp: A robust HTTP client for Android, used for making network requests efficiently.
- Retrofit: A type-safe HTTP client for Android and Java, simplifying the process of consuming RESTful APIs.

### Threading: Coroutines
We leverage coroutines for handling asynchronous operations within our SDK. This enables smoother and more intuitive concurrency management, making your app's development process more efficient.