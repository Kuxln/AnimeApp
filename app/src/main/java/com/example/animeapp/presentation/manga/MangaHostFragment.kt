package com.example.animeapp.presentation.manga

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentMangaHostBinding
import com.example.animeapp.presentation.anime.AnimeFragment
import com.example.animeapp.presentation.anime.AnimeHostFragment
import com.example.animeapp.presentation.core.ui.BaseFragment

class MangaHostFragment : BaseFragment<FragmentMangaHostBinding>(
    R.layout.fragment_manga_host
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMangaHostBinding.bind(view)

        childFragmentManager.beginTransaction()
            .addToBackStack(MangaFragment::class.java.name)
            .replace(R.id.containerMangaFragment, MangaFragment())
            .commit()
    }
}