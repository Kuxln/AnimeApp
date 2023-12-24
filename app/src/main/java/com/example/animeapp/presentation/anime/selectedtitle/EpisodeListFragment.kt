package com.example.animeapp.presentation.anime.selectedtitle

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentAnimeEpisodesBinding
import com.example.animeapp.presentation.core.ui.BaseFragment

class AnimeEpisodeListFragment : BaseFragment<FragmentAnimeEpisodesBinding>(
    R.layout.fragment_anime_episodes
) {


    companion object {
        fun newInstance(id: Int): AnimeEpisodeListFragment {
            val args = Bundle()
            args.putInt("ID", id)
            val fragmentInstance = AnimeEpisodeListFragment()
            
        }
    }
}