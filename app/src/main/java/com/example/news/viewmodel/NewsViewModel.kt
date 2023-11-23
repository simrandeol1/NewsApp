package com.example.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.model.NewsData
import com.example.news.model.NewsItemData
import com.example.news.networking.RemoteApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.coroutines.CoroutineContext

class NewsViewModel: ViewModel() {
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val viewModelScope = CoroutineScope(coroutineContext)
    private val _newsList: MutableLiveData<MutableList<NewsItemData>> = MutableLiveData()
    val newsList: LiveData<MutableList<NewsItemData>> get() = _newsList
    private val newsApi = RemoteApi()
    val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    //function to get news in bakcground
    fun fetchNews(){
        viewModelScope.launch {
            _newsList.postValue(withContext(Dispatchers.IO) {
                newsApi.getNews().articles
            })
        }
    }

    //function to sort news from old to new
    fun fetchOldToLatestNews() {
        val format: DateFormat = SimpleDateFormat(UTC_FORMAT, Locale.ENGLISH)
        val sortedList = _newsList.value?.sortedBy { format.parse(it.publishedAt) }

        _newsList.value = sortedList as MutableList
    }

    //function to sort news from new to old
    fun fetchLatestToOldNews(){
        val format: DateFormat = SimpleDateFormat(UTC_FORMAT, Locale.ENGLISH)
        val sortedList = _newsList.value?.sortedByDescending { format.parse(it.publishedAt) }

        _newsList.value = sortedList as MutableList
    }
}