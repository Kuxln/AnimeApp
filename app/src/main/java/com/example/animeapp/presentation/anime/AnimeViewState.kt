package com.example.animeapp.presentation.anime

import com.example.animeapp.data.anime.AnimeTitleData

data class AnimeViewState(
    var animeTitleData: List<AnimeTitleData>? = null,
    var animeSearchTitleData: List<AnimeTitleData>? = null,
    var searchString: String = "",
    var hasMoreData: Boolean = false,
    var searchHasMoreData: Boolean = false,
    var isLoading: Boolean = true,
    var isSearching: Boolean = false,
    var setData: Boolean = false,
    var setSearchData: Boolean = false
)