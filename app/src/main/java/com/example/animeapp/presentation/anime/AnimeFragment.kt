package com.example.animeapp.presentation.anime

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentAnimeBinding
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory
import com.example.animeapp.presentation.core.BaseFragment
import com.example.animeapp.presentation.core.PaddingItemDecoration

class AnimeFragment : BaseFragment<FragmentAnimeBinding>(
    R.layout.fragment_anime
) {
    private val viewModel: AnimeViewModel by viewModels { AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }
    private lateinit var animeAdapter: AnimeListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentAnimeBinding.bind(view)

        fragmentBinding.animeRecyclerView.addItemDecoration(PaddingItemDecoration(24))
        fragmentBinding.animeRecyclerView.visibility = View.INVISIBLE

        fragmentBinding.animeSwipeRefreshLayout.setOnRefreshListener {
            fragmentBinding.animeSwipeRefreshLayout.isRefreshing = true
            viewModel.refreshList()
        }
        animeAdapter = AnimeListAdapter(
            {
                if (fragmentBinding.animeArrowUp.visibility == View.GONE) {
                    fragmentBinding.animeArrowUp.visibility = View.VISIBLE
                }
            },
            {
                if (fragmentBinding.animeArrowUp.visibility == View.VISIBLE) {
                    fragmentBinding.animeArrowUp.visibility = View.GONE
                }
            },
            { viewModel.loadMoreItems() }
        )
        fragmentBinding.animeRecyclerView.adapter = animeAdapter
        fragmentBinding.animeArrowUp.setOnClickListener {
            fragmentBinding.animeRecyclerView.smoothScrollToPosition(0)
        }


        viewModel.liveData.observe(this.viewLifecycleOwner) { state ->
            state.animeTitleData?.let {
                animeAdapter.updateData(it, state.hasMoreData)
                if (fragmentBinding.animeSwipeRefreshLayout.isRefreshing) {
                    fragmentBinding.animeSwipeRefreshLayout.isRefreshing = false;
                }
                if (!state.isLoading) {
                    fragmentBinding.animeRecyclerView.visibility = View.VISIBLE
                    fragmentBinding.animeListProgressBar.visibility = View.INVISIBLE
                } else {
                    fragmentBinding.animeRecyclerView.visibility = View.INVISIBLE
                    fragmentBinding.animeListProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }
}