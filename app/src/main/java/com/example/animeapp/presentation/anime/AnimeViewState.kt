package com.example.animeapp.presentation.anime

import com.example.animeapp.data.AnimeTitleData

data class AnimeViewState(
    var animeTitleData: List<AnimeTitleData>? = null,
    var isLoading: Boolean = true
)