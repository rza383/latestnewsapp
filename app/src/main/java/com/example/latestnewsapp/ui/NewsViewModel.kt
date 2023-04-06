package com.example.latestnewsapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.latestnewsapp.network.*
import kotlinx.coroutines.launch

enum class NewsApiStatus {LOADING, DONE, ERROR}

class NewsViewModel: ViewModel() {

    private var _status = MutableLiveData<NewsApiStatus>()
    val status: LiveData<NewsApiStatus> = _status
    private var _news = MutableLiveData<Article>()
    val news: LiveData<Article> = _news
    private var _newsList = MutableLiveData<List<Article>>()
    val newsList: LiveData<List<Article>> = _newsList
    private var isFirstTime = false

    init{
        isFirstTime = true
    }

    fun getArticlesList(){
        viewModelScope.launch {
            _status.value = NewsApiStatus.LOADING
            try {
                _newsList.value = NewsApi.retrofitService.getNews().articleItems
                _status.value = NewsApiStatus.DONE
                if(isFirstTime){
                    _news.value = _newsList.value!![0]
                    isFirstTime = false
                }
           } catch(e: Exception){
                _status.value = NewsApiStatus.ERROR
                _newsList.value = listOf()
                Log.d("ViewModel", "${e.message}")
            }
        }
    }

    fun onArticleClicked(news: Article){
        _news.value = news
    }

}