package com.keyvani.newsapiappmvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.keyvani.newsapiappmvvm.models.Article
import com.keyvani.newsapiappmvvm.utils.Constants

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: Article) :Long

    @Delete
    suspend fun deleteNews(entity: Article)

    @Query("SELECT * FROM ${Constants.NEWS_TABLE} ORDER BY id DESC")
    fun getAllNews(): LiveData<List<Article>>

    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.NEWS_TABLE} WHERE id = :newsId)")
    suspend fun existsNews(newsId: Int): Boolean


}