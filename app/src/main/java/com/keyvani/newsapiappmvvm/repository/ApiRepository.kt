package com.keyvani.newsapiappmvvm.repository

import com.keyvani.newsapiappmvvm.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(private val api: ApiServices) {

    //Api
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) = api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) = api.searchBreakingNews(searchQuery, pageNumber)


}