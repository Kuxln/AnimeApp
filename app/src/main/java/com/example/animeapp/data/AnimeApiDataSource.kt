package com.example.animeapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnimeApiDataSource {
    private val animeApi: AnimeApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://kitsu.io/api/edge/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        animeApi = retrofit.create(AnimeApi::class.java)
    }
    suspend fun getTopAnime() : AnimeApiResponse? {
        return animeApi.getTopAnime().body()
    }


}