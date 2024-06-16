package com.example.animeapp.presentation.anime.episodes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentAnimeEpisodesBinding
import com.example.animeapp.presentation.core.ui.BaseFragment
import com.example.animeapp.presentation.core.ui.PaddingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EpisodeListFragment @Inject constructor() : BaseFragment<FragmentAnimeEpisodesBinding>(
    R.layout.fragment_anime_episodes
) {
    private val viewModel: EpisodesViewModel by viewModels()
    private lateinit var adapter: AnimeSelectedItemEpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AnimeSelectedItemEpisodesAdapter { viewModel.loadMore() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAnimeEpisodesBinding.bind(view)
        val args = this.arguments
        val id = args?.getString("ID") ?: throw IllegalArgumentException("id is required for fragment EpisodeListFragment")
        binding.recycler.adapter = adapter
        viewModel.populateData(id)

        binding.recycler.addItemDecoration(PaddingItemDecoration(24))

        viewModel.liveData.observe(this.viewLifecycleOwner) {state ->
            state.episodesData?.let {data ->
                adapter.updateData(data, state.hasMore)
            }
            if (state.isLoading) {
                binding.recycler.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.recycler.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }

        }
    }

    companion object {
        fun newInstance(id: String): EpisodeListFragment {
            val args = Bundle()
            args.putString("ID", id)
            val fragmentInstance = EpisodeListFragment()
            fragmentInstance.arguments = args
            return fragmentInstance
        }
    }
}