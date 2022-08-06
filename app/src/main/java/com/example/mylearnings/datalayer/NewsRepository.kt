package com.example.mylearnings.datalayer

import android.util.Log
import com.example.mylearnings.NetworkResponse
import com.example.mylearnings.datalayer.Util.Companion.TAG
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher,
    private val networkInstance: NewsAPI) {

    /**
     * Fetch breaking news from network in background thread using coroutine
     *
     * var response: Response<NewsResponse>? = null
            withContext(defaultDispatcher) {
            Log.d(TAG, "getBreakingNewsFromNetwork: ${Thread.currentThread().name}")
            val newsResponse = RetrofitInstance.api.getBreakingNews()
            if (newsResponse.isSuccessful) {
                response = newsResponse
            } else {
                response = newsResponse
            }
        }
        return response
     */
    suspend fun getBreakingNewsFromNetwork() = withContext(defaultDispatcher) {
        Log.d(TAG, "getBreakingNewsFromNetwork: ${Thread.currentThread().name}")
        val newsResponse = networkInstance.getBreakingNews()
        if (newsResponse.isSuccessful) {
            val newsBody = newsResponse.body()
            newsBody?.let {
                NetworkResponse.Success(it.articles)
            }
        } else {
            NetworkResponse.Error(newsResponse.errorBody().toString())
        }
    }
}