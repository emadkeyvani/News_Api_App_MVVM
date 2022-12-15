package com.keyvani.newsapiappmvvm.api

import com.keyvani.newsapiappmvvm.models.NewsResponse
import com.keyvani.newsapiappmvvm.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String ,
        @Query("page") pageNumber: Int
    ): Response<NewsResponse>


    @GET("everything")
    suspend fun searchBreakingNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int
    ): Response<NewsResponse>


}