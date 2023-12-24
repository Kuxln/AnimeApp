package com.example.animeapp.presentation.anime.selectedtitle

import com.example.animeapp.data.AnimeEpisodesData

data class EpisodesViewState (
    var episodesData: List<AnimeEpisodesData>? = null,
    var isLoading: Boolean = true,
    var hasMore: Boolean = false
)