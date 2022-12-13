package com.keyvani.newsapiappmvvm.db

import androidx.room.*
import com.keyvani.newsapiappmvvm.models.Article
import com.keyvani.newsapiappmvvm.utils.Constants

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(entity: Article)

    @Delete
    fun deleteNews(entity: Article)

    @Query("SELECT * FROM ${Constants.NEWS_TABLE}")
    fun getAllNews(): MutableList<Article>

    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.NEWS_TABLE} WHERE id = :newsId)")
    suspend fun existsNews(newsId: Int): Boolean


}