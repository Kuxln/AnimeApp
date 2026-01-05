package com.example.animeapp.presentation.anime.selectedtitle

import com.example.animeapp.data.anime.AnimeTitleDataResponse

data class AnimeSelectedItemViewState(
    var title: AnimeTitleDataResponse? = null,
    var id: String? = null,
    var isLoading: Boolean = true
)
