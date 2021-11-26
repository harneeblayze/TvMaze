package com.tvMaze.android.tvmazeapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tvMaze.android.data.model.Show
import com.tvMaze.android.data.model.TvMazeModel
import com.tvMaze.android.data.remote.service.TvMazeRemoteService
import retrofit2.HttpException
import java.io.IOException

private const val TV_MAZE_STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 30

class TvMazePagingSource(
    private val service: TvMazeRemoteService,
    private val query: String
) : PagingSource<Int, TvMazeModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvMazeModel> {
        val position = params.key ?: TV_MAZE_STARTING_PAGE_INDEX
        val apiQuery = query

        return try {
            val response = service.searchShows(apiQuery)
            val shows = response
            val nextKey = if (shows.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = shows,
                prevKey = if (position == TV_MAZE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }


    override fun getRefreshKey(state: PagingState<Int, TvMazeModel>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}