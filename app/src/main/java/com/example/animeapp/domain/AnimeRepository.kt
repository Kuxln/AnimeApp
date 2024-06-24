package com.example.animeapp.domain

import com.example.animeapp.data.anime.AnimeApiDataSource
import com.example.animeapp.domain.entity.AnimeCharacterEntity
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val api: AnimeApiDataSource
) {
    suspend fun getCharactersIds(animeId: String): List<String>? {
        return api.getCharactersList(animeId)?.data?.map { it.id }
    }

    suspend fun getCharacters(charIds: List<String>): List<AnimeCharacterEntity> {
        val chars = charIds.mapNotNull {
            api.getCharacter(it)
        }
        return chars.map {
            AnimeCharacterEntity(
                it.data?.id.orEmpty(), it.data?.attributes?.canonicalName,
                it.data?.attributes?.image?.original, true
            )
        }
    }
}