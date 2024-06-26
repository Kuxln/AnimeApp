package com.example.animeapp.domain

import android.util.Log
import com.example.animeapp.data.anime.AnimeApiDataSource
import com.example.animeapp.domain.entity.AnimeCharacterEntity
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
            pool.async {api.getCharacter(it)}
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
}