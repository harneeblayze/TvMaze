package com.tvMaze.android.data


import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.tvMaze.android.data.model.Show
import com.tvMaze.android.data.model.TvMazeModel
import com.tvMaze.android.data.model.TvMazeRemoteModel

object TvShowData{
    private val gson = Gson()

    private val remoteShowAdapter: TypeAdapter<TvMazeRemoteModel> =
        gson.getAdapter(TvMazeRemoteModel::class.java)

    fun provideRemoteTvShowsFromAssets(): List<TvMazeModel> {
        return remoteShowAdapter.fromJson(
            FileReader.readFileFromResources("tv_show.json")
        ) ?: listOf()
    }


}