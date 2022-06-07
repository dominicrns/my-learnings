package com.example.mylearnings

import com.example.mylearnings.model.Article

sealed class NetworkResponse {
    class Success(var data: List<Article>): NetworkResponse()
    class Error(var errorMessage: String): NetworkResponse()
}
