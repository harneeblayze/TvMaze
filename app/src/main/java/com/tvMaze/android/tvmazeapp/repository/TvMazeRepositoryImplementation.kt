package com.tvMaze.android.tvmazeapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tvMaze.android.data.model.Show
import com.tvMaze.android.data.model.TvMazeModel
import com.tvMaze.android.data.remote.service.TvMazeRemoteService
import com.tvMaze.android.tvmazeapp.paging.TvMazePagingSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

private const val NETWORK_PAGE_SIZE = 30

@Singleton
class TvMazeRepositoryImpl @Inject constructor(private val apiService: TvMazeRemoteService)
    : TvMazeRepository {


    override fun getSearchResultStream(query: String): Flow<PagingData<TvMazeModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TvMazePagingSource(apiService, query) }
        ).flow
    }
}