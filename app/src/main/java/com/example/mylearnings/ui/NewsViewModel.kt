package com.example.mylearnings.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mylearnings.datalayer.NewsRepository
import com.example.mylearnings.datalayer.Util.Companion.TAG
import com.example.mylearnings.model.Article

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    private val _breakingNews = MutableLiveData<List<Article>>()

    val breakingNews: LiveData<List<Article>>
        get() = _breakingNews

    private val _spinner = MutableLiveData<Boolean>()

    val spinner: LiveData<Boolean>
        get() = _spinner

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() {

        // Display spinner
        _spinner.postValue(true)

        newsRepository.getBreakingNewsFromNetwork(object : NewsRepository.NetworkCallback {
            override fun onCompleted(articles: List<Article>) {
                Log.d(TAG, "onCompleted: $articles")
                Log.d(TAG, "ViewModel onCompleted: ${Thread.currentThread().name}")
                _breakingNews.postValue(articles)
                _spinner.postValue(false)
            }

            override fun onError(msg: String) {
                Log.e(TAG, "onError: $msg")
                _spinner.value = false
            }
        })
    }
}
