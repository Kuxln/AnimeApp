package com.example.animeapp.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentTabsBinding
import com.example.animeapp.presentation.core.BaseFragment
import com.example.animeapp.presentation.anime.AnimeFragment
import com.example.animeapp.presentation.manga.MangaFragment
import com.example.animeapp.presentation.profile.ProfileFragment
import com.example.animeapp.presentation.reels.ReelsFragment
import com.example.animeapp.presentation.search.SearchFragment

class TabsFragment : BaseFragment<FragmentTabsBinding>(
    R.layout.fragment_tabs
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentTabsBinding.bind(view)

        val animeFragment = AnimeFragment()
        val mangaFragment = MangaFragment()
        val searchFragment = SearchFragment()
        val reelsFragment = ReelsFragment()
        val profileFragment = ProfileFragment()
        fragmentBinding.mainNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.animeTab -> setFragment(animeFragment)
                R.id.mangaTab -> setFragment(mangaFragment)
                R.id.searchTab -> setFragment(searchFragment)
                R.id.reelsTab -> setFragment(reelsFragment)
                R.id.profileTab -> setFragment(profileFragment)
            }
            true
        }
        setFragment(animeFragment)
    }


    private fun setFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainerView, fragment)
            .commit()
    }

}