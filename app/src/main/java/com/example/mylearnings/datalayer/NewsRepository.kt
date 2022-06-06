package com.example.mylearnings.datalayer

import android.util.Log
import com.example.mylearnings.datalayer.Util.Companion.TAG
import com.example.mylearnings.model.Article

class NewsRepository {

    /**
     * Fetch breaking news from network in background thread using executor service
     *
     * Cannot place retrofit network operation in main thread. This will throw runtime exception
     * android.os.NetworkOnMainThreadException
     */
    fun getBreakingNewsFromNetwork(callback: NetworkCallback) {
        BACKGROUND.submit {
            val networkResponse = RetrofitInstance.api.getBreakingNews().execute()
            Log.d(TAG, "getBreakingNewsFromNetwork: ${Thread.currentThread().name}")
            if (networkResponse.isSuccessful) {
                val networkBody = networkResponse.body()
                networkBody?.let {
                    callback.onCompleted(it.articles)
                }
            } else {
                callback.onError(networkResponse.errorBody().toString())
            }
        }
    }


    interface NetworkCallback {
        fun onCompleted(articles: List<Article>)
        fun onError(msg: String)
    }
}