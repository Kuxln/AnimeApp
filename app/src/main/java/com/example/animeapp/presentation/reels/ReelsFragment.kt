package com.example.animeapp.presentation.reels

import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentReelsBinding
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory
import com.example.animeapp.presentation.core.BaseFragment

class ReelsFragment : BaseFragment<FragmentReelsBinding>(
    R.layout.fragment_reels
) {
    val viewModel: ReelsViewModel by viewModels{ AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }
}