package com.example.animeapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MangaApiDataSource @Inject constructor() {
    private val mangaApi: MangaApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://kitsu.io/api/edge/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mangaApi = retrofit.create(MangaApi::class.java)
    }

    suspend fun getTopManga(): MangaApiResponse? {
        return mangaApi.getTopManga().body()
    }

    suspend fun loadMore(offset: Int): MangaApiResponse? {
        return mangaApi.loadNext(offset, 20).body()
    }
}