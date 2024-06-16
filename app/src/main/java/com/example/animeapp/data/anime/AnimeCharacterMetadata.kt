package com.example.animeapp.data.anime

data class AnimeCharacterMetadata (
    val data: Data? = null,
)

data class Data(
    val attributes: Attributes? = null,
)

data class Attributes(
    val names: Names,
)

data class Names(
    val canonicalName: String? = null
)