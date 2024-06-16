package com.example.animeapp.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentTabsBinding
import com.example.animeapp.presentation.anime.anime_tab.AnimeHostFragment
import com.example.animeapp.presentation.core.ui.BaseFragment
import com.example.animeapp.presentation.manga.MangaHostFragment
import com.example.animeapp.presentation.profile.ProfileFragment
import com.example.animeapp.presentation.reels.ReelsFragment

class TabsFragment : BaseFragment<FragmentTabsBinding>(
    R.layout.fragment_tabs
) {
    private var currentFragment: Fragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTabsBinding.bind(view)

        binding.mainNavBar.setOnItemSelectedListener {
            if (binding.mainNavBar.selectedItemId == it.itemId) {
                return@setOnItemSelectedListener false
            }

            when (it.itemId) {
                R.id.animeTab -> setFNavigationFragment(AnimeHostFragment::class.java)
                R.id.mangaTab -> setFNavigationFragment(MangaHostFragment::class.java)
                R.id.reelsTab -> setFNavigationFragment(ReelsFragment::class.java)
                R.id.profileTab -> setFNavigationFragment(ProfileFragment::class.java)
            }
            true
        }
        setFNavigationFragment(AnimeHostFragment::class.java)
    }

    override fun onBack(): Boolean {
        val fr = currentFragment
        if (fr is BaseFragment<*> && fr.onBack()) {
            return true
        } else if (childFragmentManager.backStackEntryCount  > 0) {
            childFragmentManager.popBackStack()
            return true
        } else if (binding.mainNavBar.selectedItemId != R.id.animeTab) {
            binding.mainNavBar.selectedItemId = R.id.animeTab
            return true
        }

        return false
    }

    private fun setFNavigationFragment(frClass: Class<out Fragment>) {
        val frTag = frClass.name
        var fragment = childFragmentManager.findFragmentByTag(frTag)

        val transaction = childFragmentManager.beginTransaction()
        if (fragment == null) {
            fragment = frClass.newInstance()
            transaction.add(R.id.mainFragmentContainerView, fragment, frTag)
        } else {
            transaction.show(fragment)
        }

        currentFragment?.let {
            transaction.hide(it)
        }

        currentFragment = fragment
        transaction.commit()
    }
}