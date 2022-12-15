package com.keyvani.newsapiappmvvm.repository

import com.keyvani.newsapiappmvvm.db.NewsDao
import com.keyvani.newsapiappmvvm.models.Article
import javax.inject.Inject

class DbRepository @Inject constructor(private val dao: NewsDao) {

    suspend fun upsertNews(entity: Article) = dao.upsert(entity)
    suspend fun deleteNews(entity: Article) = dao.deleteNews(entity)
    fun getAllNews() = dao.getAllNews()
}