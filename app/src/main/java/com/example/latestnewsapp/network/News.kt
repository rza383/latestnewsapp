package com.example.latestnewsapp.network
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


data class Articles (
    @Json(name="articles") val articleItems: List<Article>
)

data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val content: String?,
    val publishedAt: String?
)

