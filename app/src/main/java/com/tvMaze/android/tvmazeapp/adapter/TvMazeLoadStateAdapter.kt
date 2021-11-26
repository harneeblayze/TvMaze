package com.tvMaze.android.tvmazeapp.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class TvMazeLoadStateAdapter (private val retry: () -> Unit) : LoadStateAdapter<TvMazeLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: TvMazeLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): TvMazeLoadStateViewHolder {
        return TvMazeLoadStateViewHolder.create(parent, retry)
    }
}