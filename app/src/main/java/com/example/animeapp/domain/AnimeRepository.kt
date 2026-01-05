package com.example.animeapp.domain

import android.util.Log
import com.example.animeapp.data.anime.AnimeApiDataSource
import com.example.animeapp.data.anime.AnimeApiResponse
import com.example.animeapp.domain.entity.AnimeCharacterEntity
import com.example.animeapp.domain.entity.AnimeTitleEntity
import com.example.animeapp.presentation.anime.anime_tab.AnimeTitlePage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.newFixedThreadPoolContext
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val api: AnimeApiDataSource
) {
    private val pool = CoroutineScope(newFixedThreadPoolContext(10, "charThreads"))
    suspend fun getCharactersIds(animeId: String): List<String>? {
        return api.getCharactersList(animeId)?.data?.map { it.id }
    }

    //3.2s, 5.5s
    suspend fun getCharacters(charIds: List<String>): List<AnimeCharacterEntity> {
        Log.d("StartChar", "Start")
        val charsJobs = charIds.map {
            pool.async { api.getCharacter(it) }
        }

        val chars = charsJobs.mapNotNull { it.await() }
        Log.d("StartChar", "End")
        return chars.map {
            AnimeCharacterEntity(
                it.data?.id.orEmpty(), it.data?.attributes?.canonicalName,
                it.data?.attributes?.image?.original, true
            )
        }
    }

    suspend fun getTopAnime(): AnimeTitlePage {
        val response = api.getTopAnime()
        return responseToEntity(response)
    }

    suspend fun getAnimeTitles(offset: Int): AnimeTitlePage {
        val response = api.getAnimeTitles(offset)
        return responseToEntity(response)
    }

    suspend fun loadMoreSearchQuery(searchString: String, offset: Int): AnimeTitlePage {
        val response = api.loadMoreSearchQuery(searchString, offset)
        return responseToEntity(response)
    }

    suspend fun searchQuery(query: String): AnimeTitlePage {
        val response = api.searchQuery(query)
        return responseToEntity(response)
    }

    private fun responseToEntity(response: AnimeApiResponse?): AnimeTitlePage {
        val list = response?.data?.map {
            it.attributes?.let { attr ->
                AnimeTitleEntity(
                    it.id,
                    attr.canonicalTitle.orEmpty(),
                    attr.description.orEmpty(),
                    attr.episodeCount ?: 0,
                    attr.averageRating.orEmpty(),
                    attr.userCount ?: 0,
                    attr.startDate.orEmpty(),
                    attr.endDate.orEmpty(),
                    attr.episodeLength ?: 0,
                    attr.posterImage?.original.orEmpty()
                )
            } ?: AnimeTitleEntity(it.id)
        } ?: listOf(AnimeTitleEntity("-1"))
        return AnimeTitlePage(list, response?.links?.next != null)
    }
}
