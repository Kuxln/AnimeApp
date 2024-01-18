package com.example.animeapp.presentation.reels

import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentReelsBinding
import com.example.animeapp.presentation.core.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReelsFragment : BaseFragment<FragmentReelsBinding>(
    R.layout.fragment_reels
) {
    val viewModel: ReelsViewModel by viewModels()
}