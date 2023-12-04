package com.example.animeapp.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentAnimeBinding
import com.example.animeapp.databinding.FragmentTabsBinding
import com.example.animeapp.presentation.anime.AnimeFragment
import com.example.animeapp.presentation.anime.AnimeSelectedItemFragment
import com.example.animeapp.presentation.core.BaseFragment
import com.example.animeapp.presentation.core.MainActivityFragment
import com.example.animeapp.presentation.manga.MangaFragment
import com.example.animeapp.presentation.profile.ProfileFragment
import com.example.animeapp.presentation.reels.ReelsFragment
import com.example.animeapp.presentation.search.SearchFragment

class TabsFragment : BaseFragment<FragmentTabsBinding>(
    R.layout.fragment_tabs
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentTabsBinding.bind(view)

        val animeFragment = AnimeFragment {
            childFragmentManager.beginTransaction()
                .addToBackStack(AnimeSelectedItemFragment::class.java.name)
                .replace(R.id.mainFragmentContainerView, AnimeSelectedItemFragment.newInstance(it))
                .commit()
        }
        val mangaFragment = MangaFragment()
        val reelsFragment = ReelsFragment()
        val profileFragment = ProfileFragment()
        fragmentBinding.mainNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.animeTab -> setFragment(animeFragment)
                R.id.mangaTab -> setFragment(mangaFragment)
                R.id.reelsTab -> setFragment(reelsFragment)
                R.id.profileTab -> setFragment(profileFragment)
            }
            true
        }
        setFragment(animeFragment)
    }

    //todo
    override fun onBack(): Boolean {
        val currentFragment =
            fragmentBinding.mainFragmentContainerView.getFragment<BaseFragment<*>>()
        return if (currentFragment.onBack()) {
            Toast.makeText(requireActivity(), "dadadada", Toast.LENGTH_SHORT).show()
            true
        } else false
    }

    private fun setFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainerView, fragment)
            .commit()
    }

}