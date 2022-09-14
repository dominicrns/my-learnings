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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {

    private val _breakingNews = MutableStateFlow<List<Article>>(listOf())

    val breakingNews = _breakingNews.asStateFlow()

    private val _spinner = MutableStateFlow(true)

    val spinner = _spinner.asStateFlow()

    init {
        getBreakingNews()
    }

    private fun getBreakingNews() {

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
