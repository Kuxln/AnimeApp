package com.example.animeapp.data.anime

data class AnimeCharacterResponse(
    val data: Data? = null,
)

data class Data(
    val attributes: Attributes? = null,
)

data class Attributes(
    val canonicalName: String,
    val image: Image,
)

data class Image(
    val original: String
)

data class AnimeCharactersListResponse(
    val data: List<CharacterItem>? = null
)

data class CharacterItem(
    val id: String
)

