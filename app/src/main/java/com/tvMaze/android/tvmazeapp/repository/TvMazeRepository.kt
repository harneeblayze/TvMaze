package com.tvMaze.android.tvmazeapp.repository

import androidx.paging.PagingData
import com.tvMaze.android.data.model.Show
import com.tvMaze.android.data.model.TvMazeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TvMazeRepository {
    fun getSearchResultStream(query: String): Flow<PagingData<TvMazeModel>>
}