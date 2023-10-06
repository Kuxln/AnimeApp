package com.example.animeapp.presentation.manga

import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentMangaBinding
import com.example.animeapp.presentation.anime.AnimeViewModel
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory
import com.example.animeapp.presentation.core.BaseFragment

class MangaFragment : BaseFragment<FragmentMangaBinding>(
    R.layout.fragment_manga
){
    val viewModel : MangaViewModel by viewModels { AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }
}