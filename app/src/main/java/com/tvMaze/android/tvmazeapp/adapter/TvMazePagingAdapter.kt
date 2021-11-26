package com.tvMaze.android.tvmazeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.tvMaze.android.data.model.TvMazeModel
import com.tvMaze.android.tvmazeapp.databinding.ItemTvMazeBinding

class TvMazePagingAdapter(private val callback:(TvMazeModel?)-> Unit): PagingDataAdapter<TvMazeModel,
        TvMazeViewHolder>(TvMazeComparator()) {
    override fun onBindViewHolder(holder: TvMazeViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvMazeViewHolder {

        val binding = ItemTvMazeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TvMazeViewHolder(binding, callback, parent.context)


    }


}