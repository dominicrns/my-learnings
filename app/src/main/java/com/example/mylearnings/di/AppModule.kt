package com.example.mylearnings.di

import com.example.mylearnings.datalayer.NewsAPI
import com.example.mylearnings.datalayer.NewsRepository
import com.example.mylearnings.datalayer.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(
        coroutineDispatcher: CoroutineDispatcher,
        networkInstance: NewsAPI
    ): NewsRepository {
        return NewsRepository(coroutineDispatcher, networkInstance)
    }

    @Singleton
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideNetworkInstance(): NewsAPI = Retrofit.Builder()
        .baseUrl(Util.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)
}