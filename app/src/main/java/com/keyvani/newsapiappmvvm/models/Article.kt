package com.keyvani.newsapiappmvvm.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.keyvani.newsapiappmvvm.utils.Constants
import java.io.Serializable

@Entity(tableName = Constants.NEWS_TABLE)

data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0,
    var author: String? = "",
    var content: String?= "",
    var description: String?= "",
    var publishedAt: String?= "",
    var source: Source? = null,
    var title: String?= "",
    var url: String= "",
    var urlToImage: String?= ""
) : Serializable
