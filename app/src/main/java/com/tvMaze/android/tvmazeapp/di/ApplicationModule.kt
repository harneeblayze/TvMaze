package com.tvMaze.android.tvmazeapp.di


import com.tvMaze.android.tvmazeapp.repository.TvMazeRepository
import com.tvMaze.android.tvmazeapp.repository.TvMazeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
 abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repository: TvMazeRepositoryImpl) : TvMazeRepository
}