package com.example.animeapp.presentation.search

import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentSearchBinding
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory
import com.example.animeapp.presentation.core.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    R.layout.fragment_search
){
    val viewModel: SearchViewModel by viewModels{ AppViewModelFactory(requireActivity().applicationContext as AnimeApp)}
}