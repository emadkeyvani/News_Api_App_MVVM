package com.keyvani.newsapiappmvvm.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.keyvani.newsapiappmvvm.utils.Constants

@Entity(tableName = Constants.NEWS_TABLE)
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val title: String,
    val url: String,
    val urlToImage: String
)
