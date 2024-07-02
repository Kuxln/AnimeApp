package com.example.animeapp.domain

import android.util.Log
import com.example.animeapp.data.anime.AnimeApiDataSource
import com.example.animeapp.domain.entity.AnimeCharacterEntity
import com.example.animeapp.domain.entity.AnimeTitleEntity
import com.example.animeapp.presentation.anime.anime_tab.AnimeTitlePage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.newFixedThreadPoolContext
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val api: AnimeApiDataSource
) {
    private val pool = CoroutineScope(newFixedThreadPoolContext(10, "charThreads"))
    suspend fun getCharactersIds(animeId: String): List<String>? {
        return api.getCharactersList(animeId)?.data?.map { it.id }
    }

    //3.2, 5.5
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
        val metadata = api.getTopAnime()
        val list = metadata?.data?.map {
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
        return AnimeTitlePage(list, metadata?.links?.next != null)
    }

    suspend fun getAnimeTitles() {}

    suspend fun loadMoreSearchQuery() {}

    suspend fun searchQuery(query: String): AnimeTitlePage {
        val metadata = api.searchQuery(query)
        val list = metadata?.data?.map {
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
        return AnimeTitlePage(list, metadata?.links?.next != null)
    }


}

