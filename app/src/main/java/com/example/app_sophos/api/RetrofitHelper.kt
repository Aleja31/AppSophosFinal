package com.example.app_sophos.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://6w33tkx4f9.execute-api.us-east-1.amazonaws.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> buildService(service: Class<T>):T{
        return retrofit.create(service)
    }
}