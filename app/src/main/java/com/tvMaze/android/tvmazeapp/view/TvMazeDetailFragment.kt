package com.tvMaze.android.tvmazeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tvMaze.android.core.utils.getProgressDrawable
import com.tvMaze.android.core.utils.loadShow
import com.tvMaze.android.tvmazeapp.R
import com.tvMaze.android.tvmazeapp.databinding.FragmentTvMazeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvMazeDetailFragment : Fragment() {
    private lateinit var binding: FragmentTvMazeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvMazeDetailBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title =  arguments?.getString("name")
        val url = arguments?.getString("url")

        binding.apply {
            imgShowDetail.loadShow(url, getProgressDrawable(requireContext()))
            tvShowTitle.text = title?:"This Show Has No Title"
        }

    }
}