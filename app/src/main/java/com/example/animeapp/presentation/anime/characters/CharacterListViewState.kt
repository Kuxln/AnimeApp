package com.example.animeapp.presentation.anime.characters

data class CharacterListViewState(
    var animeId: String? = null,
    var charactersIds: List<String>? = null,
    var pageList: List<CharListPage>? = null
)