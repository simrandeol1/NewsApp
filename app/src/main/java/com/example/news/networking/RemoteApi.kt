package com.example.news.networking

import android.util.Log
import com.example.news.NewsAdapter
import com.example.news.model.NewsData
import com.google.gson.Gson
import com.google.gson.JsonParser
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

// system api to fetch news from api
class RemoteApi {

    private val BASE_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"
    private val TAG = "RemoteApi"

    fun getNews(): NewsData{
        val connection = URL(BASE_URL).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type","application/json")
        connection.setRequestProperty("Accept","application/json")
        connection.connectTimeout = 10000
        connection.readTimeout = 10000

        try{
            val reader = InputStreamReader(connection.inputStream,"UTF-8")
            val response = StringBuilder()
            reader.use{
                val bufferedReader = BufferedReader(reader)
                bufferedReader.forEachLine {
                    response.append(it.trim())
                }
            }
            val newsData = Gson().fromJson(response.toString(), NewsData::class.java)

            Log.d(TAG,"In_Success $newsData")

            return newsData
        }
        catch (e: Exception){
            Log.d(TAG,"In_Error ${e.printStackTrace()}")
        }
        return NewsData("", arrayListOf())
    }
}