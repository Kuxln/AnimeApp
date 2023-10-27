package com.example.animeapp.presentation.manga

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentMangaBinding
import com.example.animeapp.presentation.anime.AnimeViewModel
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory
import com.example.animeapp.presentation.core.BaseFragment
import com.example.animeapp.presentation.core.PaddingItemDecoration

class MangaFragment : BaseFragment<FragmentMangaBinding>(
    R.layout.fragment_manga
) {
    private val viewModel: MangaViewModel by viewModels { AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }
    private lateinit var mangaAdapter: MangaListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentMangaBinding.bind(view)



        with(fragmentBinding) {
            mangaRecyclerView.addItemDecoration(PaddingItemDecoration(24))
            mangaRecyclerView.visibility = View.INVISIBLE

            mangaSwipeRefreshLayout.setColorSchemeResources(R.color.orange)
            mangaSwipeRefreshLayout.setOnRefreshListener {
                mangaSwipeRefreshLayout.isRefreshing = true
                viewModel.refreshList()
            }

            mangaAdapter = MangaListAdapter(
                {
                    if (mangaArrowUp.visibility == View.GONE) {
                        mangaArrowUp.visibility = View.VISIBLE
                    }
                },
                {
                    if (mangaArrowUp.visibility == View.VISIBLE) {
                        mangaArrowUp.visibility = View.GONE
                    }
                },
                { viewModel.loadMoreItems() }
            )

            mangaRecyclerView.adapter = mangaAdapter
            mangaArrowUp.setOnClickListener {
                mangaRecyclerView.smoothScrollToPosition(0)
            }
        }

        viewModel.liveData.observe(this.viewLifecycleOwner) { state ->
            state.mangaTitleData?.let {
                mangaAdapter.updateData(it, state.hasMoreData)
                with(fragmentBinding) {
                    if (mangaSwipeRefreshLayout.isRefreshing) {
                        mangaSwipeRefreshLayout.isRefreshing = false
                    }

                    if (!state.isLoading) {
                        mangaRecyclerView.visibility = View.VISIBLE
                        mangaListProgressBar.visibility = View.INVISIBLE
                    } else {
                        mangaRecyclerView.visibility = View.INVISIBLE
                        mangaListProgressBar.visibility = View.VISIBLE
                    }
                }

            }
        }
    }
}