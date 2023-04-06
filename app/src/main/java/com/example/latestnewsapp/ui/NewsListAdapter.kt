package com.example.latestnewsapp.ui

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.latestnewsapp.R
import com.example.latestnewsapp.databinding.ListViewItemBinding
import com.example.latestnewsapp.network.Article
import java.time.Clock
import java.time.Instant.now
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class NewsListAdapter(private val clickListener: NewsListener):
    ListAdapter<Article, NewsListAdapter.NewsViewHolder>(DiffCallback) {



    class NewsViewHolder(
       var binding: ListViewItemBinding

    ): RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: NewsListener, article: Article, author: String, date: String) {
            binding.article = article
            binding.date = date
            binding.author = author
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(
            ListViewItemBinding.inflate(layoutInflater )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        val author =  article.author ?: holder.itemView.context.getString(R.string.noAuthor)
        val date = article.publishedAt ?: Calendar.getInstance().time.toString()
        holder.bind(clickListener, article, author, date)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.author == newItem.author
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.content == newItem.content && oldItem.description == newItem.description
        }
    }
}

class NewsListener(val clickListener: (news: Article) -> Unit ){
    fun onClick(news: Article) = clickListener(news)
}