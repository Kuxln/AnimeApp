package com.example.animeapp.presentation.anime.selectedtitle

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitleData
import com.example.animeapp.databinding.FragmentAnimeEpisodesBinding
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory
import com.example.animeapp.presentation.core.ui.BaseFragment

class EpisodeListFragment : BaseFragment<FragmentAnimeEpisodesBinding>(
    R.layout.fragment_anime_episodes
) {
    private val viewModel: EpisodesViewModel by viewModels { AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }
    private lateinit var adapter: AnimeSelectedItemEpisodesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = AnimeSelectedItemEpisodesAdapter { viewModel.loadMore() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentAnimeEpisodesBinding.bind(view)
        val args = this.arguments
        val id = args?.getString("ID") ?: throw IllegalArgumentException("id is required for fragment EpisodeListFragment")
        binding.recycler.adapter = adapter
        viewModel.populateData(id)



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