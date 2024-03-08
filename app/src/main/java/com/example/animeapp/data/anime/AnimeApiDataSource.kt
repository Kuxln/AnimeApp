package com.example.animeapp.data.anime

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