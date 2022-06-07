package com.example.mylearnings.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylearnings.NetworkResponse
import com.example.mylearnings.datalayer.NewsRepository
import com.example.mylearnings.datalayer.Util.Companion.TAG
import com.example.mylearnings.model.Article
import kotlinx.coroutines.launch

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
        _spinner.value = true

        viewModelScope.launch {
            val response = newsRepository.getBreakingNewsFromNetwork()
            when (response) {
                is NetworkResponse.Success -> {
                    _breakingNews.value = response.data
                    _spinner.value = false
                }
                is NetworkResponse.Error -> {
                    Log.d(TAG, "getBreakingNews: Error response ${response.errorMessage}")
                    _spinner.value = false
                }
                else -> {
                    Log.d(TAG, "getBreakingNews: Default case")
                }
            }
            Log.d(TAG, "getBreakingNews viewModel: ${Thread.currentThread().name}")
        }
    }
}
