package com.example.animeapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

class MangaApiDataSource(
    private val mangaApi: MangaApi
) {
    suspend fun getTopManga(): MangaApiResponse? {
        return mangaApi.getTopManga().body()
    }

    suspend fun loadMore(offset: Int): MangaApiResponse? {
        return mangaApi.loadNext(offset, 20).body()
    }
}