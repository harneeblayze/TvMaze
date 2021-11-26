package com.tvMaze.android.tvmazeapp.di


import com.tvMaze.android.data.remote.service.RemoteServiceFactory
import com.tvMaze.android.data.remote.service.TvMazeRemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideTvMazeService() : TvMazeRemoteService = RemoteServiceFactory.buildTvMazeRemoteService()
}