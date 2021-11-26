package com.tvMaze.android.tvmazeapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.tvMaze.android.core.utils.SpacesItemDecoration
import com.tvMaze.android.tvmazeapp.R
import com.tvMaze.android.tvmazeapp.adapter.TvMazeLoadStateAdapter
import com.tvMaze.android.tvmazeapp.adapter.TvMazePagingAdapter
import com.tvMaze.android.tvmazeapp.databinding.FragmentTvMazeListBinding
import com.tvMaze.android.tvmazeapp.viewmodel.TvMazeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvMazeListFragment : Fragment() {
    private val viewModel: TvMazeViewModel by viewModels()
    private lateinit var showAdapter: TvMazePagingAdapter
    private lateinit var binding: FragmentTvMazeListBinding
    private var searchJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvMazeListBinding.inflate(inflater, container, false)
        showAdapter = TvMazePagingAdapter {
            findNavController().navigate(TvMazeListFragmentDirections.actionTvMazeListFragmentToTvMazeDetailFragment(it?.show?.image?.medium, it?.show?.name))
        }

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showUiState()
        bindUItoAdapter()
        listenToQueryInSearchView()




        lifecycleScope.launch {
            showAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvShows.scrollToPosition(0) }
        }
    }


    private fun showUiState(){
        showAdapter.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && showAdapter.itemCount == 0
            showEmptyList(isListEmpty)

            binding.rvShows.isVisible = loadState.source.refresh is LoadState.NotLoading

            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireActivity(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }


    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyState.visibility = View.VISIBLE
            binding.rvShows.visibility = View.GONE
        } else {
            binding.emptyState.visibility = View.GONE
            binding.rvShows.visibility = View.VISIBLE
        }
    }

    private fun bindUItoAdapter(){
        binding.rvShows.apply {
            adapter = showAdapter.withLoadStateHeaderAndFooter(
                header = TvMazeLoadStateAdapter { showAdapter.retry() },
                footer = TvMazeLoadStateAdapter { showAdapter.retry() }
            )
            layoutManager = GridLayoutManager(requireActivity(), 2)
            addItemDecoration(SpacesItemDecoration(30))
        }

    }

    private fun listenToQueryInSearchView(){
        binding.svShows.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    viewModel.searchShows(newText).collectLatest {
                        showAdapter.submitData(it)
                    }
                }

                return true
            }
        })
    }

}