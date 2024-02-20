package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.model.Cursors
import com.gocardless.gocardlesssdk.model.Customers
import com.gocardless.gocardlesssdk.model.Meta
import com.gocardless.gocardlesssdk.network.Error
import com.gocardless.gocardlesssdk.network.GoCardlessAPI
import com.gocardless.gocardlesssdk.network.NetworkResult
import com.gocardless.gocardlesssdk.network.Success

class CustomerService(private val goCardlessAPI: GoCardlessAPI) {
    suspend fun all(): NetworkResult<Customers> {
        val response = goCardlessAPI.all()

        return if (response.isSuccessful) {
            Success(response.body() ?: Customers(emptyList(), Meta(Cursors(null, null), 0)))
        } else {
            Error(response.errorBody()?.toString() ?: "")
        }
    }

    suspend fun delete(customerId: String): NetworkResult<Unit> {
        val response = goCardlessAPI.delete(customerId)

        return if (response.isSuccessful) {
            Success(Unit)
        } else {
            Error(response.errorBody()?.toString() ?: "")
        }
    }
}