package com.example.animeapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AnimeApiDataSource (
    private val animeApi: AnimeApi
){
    suspend fun getTopAnime() : AnimeApiResponse? {
        return animeApi.getTopAnime().body()
    }

    suspend fun getAnimeTitles(offset: Int) : AnimeApiResponse? {
        return animeApi.loadNext(offset, 20).body()
    }

    suspend fun getAnimeEpisodes(id: String, offset: Int) : AnimeEpisodesResponse? {
        return animeApi.getEpisodes(id, 20, offset).body()
    }
}