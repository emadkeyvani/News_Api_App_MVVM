package com.keyvani.newsapiappmvvm.models


import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article?>?,
    @SerializedName("status")
    val status: String?, // ok
    @SerializedName("totalResults")
    val totalResults: Int? // 14809
) {
    data class Article(
        @SerializedName("author")
        val author: String?, // Paul Töpsch
        @SerializedName("content")
        val content: String?, // Nach den Massenentlassungen bei Twitter seit Übernahme der Social-Media-Plattform durch Tesla-CEO Elon Musk haben auch Meta und Amazon ihre Belegschaft reduziert. Zwischen dem 9. und 16. November ent… [+2077 chars]
        @SerializedName("description")
        val description: String?, // Nach den Massenentlassungen bei Twitter seit Übernahme der Social-Media-Plattform durch Tesla-CEO Elon Musk haben auch Meta und Amazon ihre ...
        @SerializedName("publishedAt")
        val publishedAt: String?, // 2022-12-12T10:18:06Z
        @SerializedName("source")
        val source: Source?,
        @SerializedName("title")
        val title: String?, // Kündigungswelle im Tech-Sektor reißt nicht ab
        @SerializedName("url")
        val url: String?, // https://winfuture.de/infografik/25920/Kuendigungswelle-im-Tech-Sektor-reisst-nicht-ab-1670840327.html
        @SerializedName("urlToImage")
        val urlToImage: String? // https://i.wfcdn.de/teaser/1920/54486.jpg
    ) {
        data class Source(
            @SerializedName("id")
            val id: String?, // independent
            @SerializedName("name")
            val name: String? // WinFuture
        )
    }
}