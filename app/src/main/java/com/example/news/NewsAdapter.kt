package com.example.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.news.model.NewsItemData

// adapter for new list
class NewsAdapter(private val context: Context, val newsData: List<NewsItemData>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false))
    }

    override fun getItemCount(): Int {
        return newsData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = newsData[position].title
        holder.author.text = newsData[position].author
        holder.description.text = newsData[position].description
        holder.publishedAt.text = newsData[position].publishedAt
        if(newsData[position].urlToImage != null) {
            Glide.with(context)
                .load(newsData[position].urlToImage)
                .placeholder(R.drawable.ic_news_)
                .error(R.drawable.ic_news_)
                .fallback(R.drawable.ic_news_)
                .into(holder.image)
        }
        else
            holder.image.visibility = View.GONE
        holder.itemView.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newsData[position].url))
            context.startActivity(browserIntent)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val title = itemView.findViewById<TextView>(R.id.title)
        val author = itemView.findViewById<TextView>(R.id.author)
        val description = itemView.findViewById<TextView>(R.id.description)
        val publishedAt = itemView.findViewById<TextView>(R.id.published)
    }
}