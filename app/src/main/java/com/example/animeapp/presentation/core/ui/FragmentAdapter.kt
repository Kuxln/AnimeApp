package com.example.animeapp.presentation.core.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.animeapp.presentation.anime.characters.CharacterListFragment
import com.example.animeapp.presentation.anime.episodes.EpisodeListFragment

class FragmentAdapter(
    fragment: Fragment,
    val id: String
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EpisodeListFragment.newInstance(id)
            1 -> CharacterListFragment.newInstance(id)
            2 -> EpisodeListFragment.newInstance(id)

            else -> EpisodeListFragment.newInstance(id)
        }
    }
}
