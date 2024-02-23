package com.gocardless.gocardlesssdk.service

import com.gocardless.gocardlesssdk.network.GoCardlessApi

class CustomerService(private val goCardlessAPI: GoCardlessApi) {
//    suspend fun all(): NetworkResult<Customers> {
//        val response = goCardlessAPI.all()
//
//        return if (response.isSuccessful) {
//            Success(response.body() ?: Customers(emptyList(), Meta(Cursors(null, null), 0)))
//        } else {
//            Error(response.errorBody()?.toString() ?: "")
//        }
//    }
//
//    suspend fun delete(customerId: String): NetworkResult<Unit> {
//        val response = goCardlessAPI.delete(customerId)
//
//        return if (response.isSuccessful) {
//            Success(Unit)
//        } else {
//            Error(response.errorBody()?.toString() ?: "")
//        }
//    }
}