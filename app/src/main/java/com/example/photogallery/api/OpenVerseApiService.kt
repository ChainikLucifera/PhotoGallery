package com.example.photogallery.api

import com.example.photogallery.models.OpenVerseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenVerseApiService {
    @GET("images/")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20
    ) : Response<OpenVerseResponse>
}