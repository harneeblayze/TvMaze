package com.tvMaze.android.data.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RemoteServiceFactory {
    fun buildOkhttpLogger(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return loggingInterceptor
    }

    fun buildOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    fun buildTvMazeService(okHttpClient: OkHttpClient): TvMazeRemoteService{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(TvMazeRemoteService::class.java)
    }

    fun buildTvMazeRemoteService() : TvMazeRemoteService{
        return buildTvMazeService(buildOkhttpClient(buildOkhttpLogger()))
    }
}