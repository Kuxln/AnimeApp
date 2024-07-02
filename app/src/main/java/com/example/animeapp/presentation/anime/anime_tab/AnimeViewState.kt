package com.example.animeapp.presentation.anime.anime_tab

import com.example.animeapp.data.anime.AnimeTitleData
import com.example.animeapp.domain.entity.AnimeTitleEntity

data class AnimeViewState(
    var animeTitleData: List<AnimeTitleEntity>? = null,
    var animeSearchTitleData: List<AnimeTitleEntity>? = null,
    var searchString: String = "",
    var hasMoreData: Boolean = false,
    var searchHasMoreData: Boolean = false,
    var isLoading: Boolean = true,
    var isSearching: Boolean = false,
    var setData: Boolean = false,
    var setSearchData: Boolean = false
)