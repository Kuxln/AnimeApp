package com.example.animeapp.presentation.profile

import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentProfileBinding
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory
import com.example.animeapp.presentation.core.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    R.layout.fragment_profile
){
    val viewModel: ProfileViewModel by viewModels{ AppViewModelFactory(requireActivity().applicationContext as AnimeApp)}
}