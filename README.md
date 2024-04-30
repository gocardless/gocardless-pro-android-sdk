# GoCardless SDK Example App

The GoCardless Android SDK is a tool that enables developers to integrate GoCardless payments into their Android applications. 
To help developers get started, a sample app has been created that demonstrates how to use the SDK. 
The app provides a clear and practical example of how to implement GoCardless payments within an Android app.

## Getting started

```xml
<dependency>
    <groupId>com.gocardless</groupId>
    <artifactId>gocardlesssdk</artifactId>
    <version>1.0.1</version>
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

## Supported Services
Currently we support the following services and their functions

### Billing Request
- `createBillingRequest`: Creates a Billing Request, enabling you to collect all types of GoCardless payments
- `collectCustomerDetails`: If the billing request has a pending collect_customer_details action, this endpoint can be used to collect the details in order to complete it.
- `collectBankAccount`: If the billing request has a pending collect_bank_account action, this endpoint can be used to collect the details in order to complete it.
- `confirmPayerDetails`: This is needed when you have a mandate request. As a scheme compliance rule we are required to allow the payer to crosscheck the details entered by them and confirm it.
- `fulfil`: If a billing request is ready to be fulfilled, call this endpoint to cause it to fulfil, executing the payment.
- `cancel`: Immediately cancels a billing request, causing all billing request flows to expire.
- `notify`: Notifies the customer linked to the billing request, asking them to authorise it.
- `getBillingRequest`: Fetches a billing request
- `listBillingRequests`: Returns a cursor-paginated list of your billing requests.

### Billing Request Flow
- `createBillingRequestFlow`: Creates a new billing request flow.

### Payment
- `createPaymentRequest`: Creates a new payment object.
- ``

## Examples

Note: To be able to make any request, you must initialise the SDK.

### Fetching List of Billing Requests

Ensure that all requests are made within the correct `Scope` before launching.

```kotlin
viewModelScope.launch {
    val response = GoCardlessSDK.billingRequestService.listBillingRequests()
}
```

The response can be of two types: `ApiSuccess` or `ApiError`.

- `ApiSuccess.value` Contains the result of the operation.
- `ApiError.error` Contains details of the encountered error.

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
