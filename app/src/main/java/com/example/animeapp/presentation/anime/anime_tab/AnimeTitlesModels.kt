package com.example.animeapp.presentation.anime.anime_tab

import com.example.animeapp.domain.entity.AnimeTitleEntity

data class AnimeTitlePage (
    val data: List<AnimeTitleEntity>,
    val hasNext: Boolean = false
)