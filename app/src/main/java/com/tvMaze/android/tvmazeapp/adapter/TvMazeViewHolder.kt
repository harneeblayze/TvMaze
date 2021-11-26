package com.tvMaze.android.tvmazeapp.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.tvMaze.android.core.utils.getProgressDrawable
import com.tvMaze.android.core.utils.loadShow

import com.tvMaze.android.data.model.TvMazeModel
import com.tvMaze.android.tvmazeapp.databinding.ItemTvMazeBinding

class TvMazeViewHolder(
    private val binding: ItemTvMazeBinding,
    private val callback:(TvMazeModel?) -> Unit, val context: Context

) : RecyclerView.ViewHolder(binding.root){
    fun bindTo(tvModel: TvMazeModel?){
        binding.apply {
            sivShowImage.loadShow(tvModel?.show?.image?.medium, getProgressDrawable(context))
            tvShowName.text = tvModel?.show?.name

        }

        itemView.setOnClickListener {
            callback.invoke(tvModel)
        }
    }

}