package com.tvMaze.android.tvmazeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tvMaze.android.data.model.TvMazeModel
import com.tvMaze.android.tvmazeapp.repository.TvMazeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
@HiltViewModel
class TvMazeViewModel@Inject constructor(
    private var repository: TvMazeRepository
): ViewModel()  {

    private var currentQueryValue: String? = null

    private var currentSearchResult: Flow<PagingData<TvMazeModel>>? = null

    fun searchShows(queryString: String): Flow<PagingData<TvMazeModel>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<TvMazeModel>> = repository.getSearchResultStream(queryString)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}