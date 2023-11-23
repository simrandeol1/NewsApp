package com.example.news

import android.Manifest
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.news.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsViewModel = NewsViewModel()
        newsViewModel.fetchNews()

        val tryAgain = findViewById<TextView>(R.id.try_again)
        tryAgain.setOnClickListener {
            newsViewModel.fetchNews()
        }

        if(!isNetworkAvailable())
            tryAgain.visibility = View.VISIBLE

        //list for news
        val recyclerView = findViewById<RecyclerView>(R.id.news_list)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    Snackbar.make(findViewById(android.R.id.content),"Permission Granted", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content),"Permission Denied",Snackbar.LENGTH_LONG).show();
                }
            }
        }
        newsViewModel.newsList.observe(this, Observer {
            if(it == null || it.size == 0){
                tryAgain.visibility = View.VISIBLE
            }
            else
                tryAgain.visibility = View.GONE
            val adapter = NewsAdapter(this, it)

            recyclerView.adapter = adapter
        })

        val oldToLatest = findViewById<TextView>(R.id.sort_dsc)
        val latestToOld = findViewById<TextView>(R.id.sort_asc)

        //button to sort old to new
        oldToLatest.setOnClickListener {
            newsViewModel.fetchOldToLatestNews()
        }

        //button to sort latest to new
        latestToOld.setOnClickListener {
            newsViewModel.fetchLatestToOldNews()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}