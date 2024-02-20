package com.gunhansancar.mvvmapp.network.model

sealed class NetworkResult<T>

data class Success<T>(val value: T) : NetworkResult<T>()
data class Error<T>(val message: String) : NetworkResult<T>()
object Loading : NetworkResult<Any>()