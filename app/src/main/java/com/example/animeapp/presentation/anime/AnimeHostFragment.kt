package com.example.animeapp.presentation.anime

import android.os.Bundle
import android.view.View
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitleData
import com.example.animeapp.databinding.FragmentAnimeHostBinding
import com.example.animeapp.presentation.anime.selectedtitle.AnimeSelectedItemFragment
import com.example.animeapp.presentation.core.ui.BaseFragment

class AnimeHostFragment : AnimeFragment.AnimeFragmentCallback,
    BaseFragment<FragmentAnimeHostBinding>(
        R.layout.fragment_anime_host
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        childFragmentManager.beginTransaction()
            .addToBackStack(AnimeFragment::class.java.name)
            .replace(R.id.containerAnimeFragment, AnimeFragment())
            .commit()
    }

    override fun onAnimeClicked(titleData: AnimeTitleData) {
        childFragmentManager.beginTransaction()
            .addToBackStack(AnimeSelectedItemFragment::class.java.name)
            .replace(
                R.id.containerAnimeFragment,
                AnimeSelectedItemFragment.newInstance(titleData)
            )
            .commit()
    }
}