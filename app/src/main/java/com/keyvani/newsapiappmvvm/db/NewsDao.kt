package com.keyvani.newsapiappmvvm.db

import androidx.room.*
import com.keyvani.newsapiappmvvm.models.NewsEntity
import com.keyvani.newsapiappmvvm.utils.Constants

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(entity: NewsEntity)

    @Delete
    suspend fun deleteNews(entity: NewsEntity)

    @Query("SELECT * FROM ${Constants.NEWS_TABLE}")
    fun getAllNews(): MutableList<NewsEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.NEWS_TABLE} WHERE id = :newsId)")
    suspend fun existsNews(newsId: Int): Boolean


}