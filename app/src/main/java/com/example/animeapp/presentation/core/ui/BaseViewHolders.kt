package com.example.animeapp.presentation.core.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.databinding.ListItemEmptyBinding
import com.example.animeapp.databinding.ListLoadingProgressBarBinding

class LoadingProgressBarViewHolder(
    binding: ListLoadingProgressBarBinding
) : RecyclerView.ViewHolder(binding.root)

class EmptyViewHolder(
    binding: ListItemEmptyBinding
) : RecyclerView.ViewHolder(binding.root)