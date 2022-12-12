package com.keyvani.newsapiappmvvm.repository

import com.keyvani.newsapiappmvvm.api.ApiServices
import com.keyvani.newsapiappmvvm.utils.Constants
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api : ApiServices) {
    suspend fun getBreakingNews(countryCode: String,pageNumber: Int,apikey: String)
    = api.getBreakingNews("us",1,"ecf3da52e8da449bb5effb0b1b940725")
}