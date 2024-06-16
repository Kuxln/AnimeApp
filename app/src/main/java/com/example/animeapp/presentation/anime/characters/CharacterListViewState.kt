package com.example.animeapp.presentation.anime.characters

import com.example.animeapp.data.anime.AnimeCharacterResponse
import com.example.animeapp.data.anime.AnimeCharactersListResponse

data class CharacterListViewState(
    var id: String? = null,
    var charactersList: MutableList<AnimeCharacterResponse> = mutableListOf(),
    var charactersIdList: AnimeCharactersListResponse?= null,
)