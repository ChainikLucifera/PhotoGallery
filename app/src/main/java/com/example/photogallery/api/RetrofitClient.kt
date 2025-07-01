package com.example.photogallery.api

import retrofit2.Retrofit
import com.example.photogallery.api.OpenVerseApiService
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.openverse.org/v1/"

    val apiService: OpenVerseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenVerseApiService::class.java)
    }

}