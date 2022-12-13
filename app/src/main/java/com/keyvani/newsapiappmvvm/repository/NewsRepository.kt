package com.keyvani.newsapiappmvvm.repository

import com.keyvani.newsapiappmvvm.api.ApiServices
import com.keyvani.newsapiappmvvm.db.NewsDao
import com.keyvani.newsapiappmvvm.models.NewsEntity
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: ApiServices, private val dao: NewsDao) {

    //Api
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) = api.getBreakingNews(countryCode, pageNumber)

    //Database
    suspend fun insertNews(entity: NewsEntity) = dao.insertNews(entity)
    suspend fun deleteNews(entity: NewsEntity) = dao.deleteNews(entity)
    suspend fun existNews(id: Int) = dao.existsNews(id)
    fun getAllNews() = dao.getAllNews()
}