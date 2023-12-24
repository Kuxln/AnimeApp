package com.example.animeapp.presentation.core.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.animeapp.presentation.anime.selectedtitle.EpisodeListFragment
import com.example.animeapp.presentation.profile.ProfileFragment
import com.example.animeapp.presentation.reels.ReelsFragment

class FragmentAdapter(fragment: Fragment, val id: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EpisodeListFragment.newInstance(id)
            1 -> EpisodeListFragment.newInstance(id)
            2 -> EpisodeListFragment.newInstance(id)

            else -> EpisodeListFragment.newInstance(id)
        }
    }
}