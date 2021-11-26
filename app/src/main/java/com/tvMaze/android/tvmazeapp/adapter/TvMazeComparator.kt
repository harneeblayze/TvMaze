package com.tvMaze.android.tvmazeapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tvMaze.android.data.model.TvMazeModel

class TvMazeComparator:DiffUtil.ItemCallback<TvMazeModel>() {
    override fun areItemsTheSame(oldItem: TvMazeModel, newItem: TvMazeModel): Boolean =
        oldItem.show.id == newItem.show.id


    override fun areContentsTheSame(oldItem: TvMazeModel, newItem: TvMazeModel): Boolean =
        oldItem == newItem

}