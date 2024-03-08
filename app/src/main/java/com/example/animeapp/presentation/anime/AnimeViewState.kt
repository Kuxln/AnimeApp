package com.example.animeapp.presentation.anime

import com.example.animeapp.data.anime.AnimeTitleData

data class AnimeViewState(
    var animeTitleData: List<AnimeTitleData>? = null,
    var isLoading: Boolean = true,
    var hasMoreData: Boolean = false,
)