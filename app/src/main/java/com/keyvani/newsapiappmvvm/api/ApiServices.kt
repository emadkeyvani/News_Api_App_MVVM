package com.keyvani.newsapiappmvvm.api

import com.keyvani.newsapiappmvvm.models.NewsResponse
import com.keyvani.newsapiappmvvm.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("top-headlines")

    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "us",
        @Query("page") pageNumber: Int = 1,
        @Query("apikey") apikey: String = "ecf3da52e8da449bb5effb0b1b940725"
    ): Response<MutableList<NewsResponse.Article>>


    @GET("everything")

    suspend fun searchBreakingNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apikey") apikey: String = Constants.API_KEY
    ): Response<NewsResponse.Article>


}