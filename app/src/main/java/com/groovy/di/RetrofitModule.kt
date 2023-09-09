package com.groovy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    @Singleton
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://ngrok-free.app/groovy/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}