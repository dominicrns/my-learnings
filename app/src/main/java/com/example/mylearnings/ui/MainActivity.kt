package com.example.mylearnings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.mylearnings.databinding.ActivityMainBinding
import com.example.mylearnings.datalayer.Util.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsViewModel.breakingNews.observe(this) {
            Log.d(TAG, "onCreate: ${Thread.currentThread().name}")
            it?.let {
                for (article in it) {
                    Log.d(TAG, "onCreate: ${article.title}")
                    binding.title.text = article.title
                }
            }
        }

        newsViewModel.spinner.observe(this) { value ->
            binding.progressBar.visibility = if(value) View.VISIBLE else View.GONE
        }
    }
}