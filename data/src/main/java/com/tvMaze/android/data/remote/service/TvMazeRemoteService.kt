package com.tvMaze.android.data.remote.service

import com.tvMaze.android.data.model.TvMazeModel
import com.tvMaze.android.data.model.TvMazeRemoteModel
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeRemoteService {
    @GET(" /search/shows")
    suspend fun searchShows(@Query("q") query:String): TvMazeRemoteModel
}