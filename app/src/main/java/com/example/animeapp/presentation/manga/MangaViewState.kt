package com.example.animeapp.presentation.manga

import com.example.animeapp.data.manga.MangaTitleData

data class MangaViewState(
    var mangaTitleData: List<MangaTitleData>? = null,
    var hasMoreData: Boolean = false,
    var isLoading: Boolean = true

)
