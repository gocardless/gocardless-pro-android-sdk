package com.gunhansancar.mvvmapp.network.catfacts

import retrofit2.Response
import retrofit2.http.GET

interface CatFactsService {

    @GET("facts")
    suspend fun facts(): Response<List<CatFact>>
}