package com.keyvani.newsapiappmvvm.models


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    val articles: MutableList<Article?>?,
    @SerializedName("status")
    val status: String?, // ok
    @SerializedName("totalResults")
    val totalResults: Int? // 14809
)
