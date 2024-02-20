package com.gunhansancar.mvvmapp.network.catfacts

import com.gunhansancar.mvvmapp.network.model.Error
import com.gunhansancar.mvvmapp.network.model.NetworkResult
import com.gunhansancar.mvvmapp.network.model.Success

class CatFactsRepository(private val service: CatFactsService) {
    suspend fun facts(): NetworkResult<List<CatFact>> {
        val response = service.facts()
        return if (response.isSuccessful) {
            Success(response.body() ?: emptyList())
        } else {
            Error(response.errorBody()?.toString() ?: "")
        }
    }
}