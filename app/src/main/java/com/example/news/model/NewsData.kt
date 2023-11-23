package com.example.news.model

data class NewsData(val status: String, val articles: ArrayList<NewsItemData>)
data class NewsItemData(val author: String, val title: String, val description: String, val url: String, val urlToImage: String, val publishedAt: String)
