package com.keyvani.newsapiappmvvm.repository

import com.keyvani.newsapiappmvvm.db.NewsDao
import com.keyvani.newsapiappmvvm.models.Article
import javax.inject.Inject

class DbRepository @Inject constructor(private val dao: NewsDao) {

    suspend fun insertNews(entity: Article) = dao.insertNews(entity)
    fun deleteNews(entity: Article) = dao.deleteNews(entity)
    suspend fun existNews(id: Int) = dao.existsNews(id)
    fun getAllNews() = dao.getAllNews()
}