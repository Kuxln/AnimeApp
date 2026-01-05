package com.example.animeapp.presentation.anime.characters

data class CharacterListViewState(
    var animeId: String? = null,
    var charactersIds: List<String>? = null,
    var pageList: List<CharListPage>? = null,
    var isLoading: Boolean = true,
    var hasMore: Boolean = true
)
