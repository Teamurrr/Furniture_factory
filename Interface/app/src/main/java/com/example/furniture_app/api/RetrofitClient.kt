package com.example.furniture_app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {

    // private const val BASE_URL = "http://192.168.0.164:8080/" // lg1

   // private const val BASE_URL = "http://192.168.0.101:8080/" // luntik

    private const val BASE_URL = "http://10.168.198.201:8080/" // ggwp a




    val apiService: ApiService by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    }
}