package com.example.animeapp.data

import retrofit2.Response
import retrofit2.http.GET

interface AnimeApi {
    @GET("anime?page[limit]=20&page[offset]=0")
    suspend fun getTopAnime() : Response<AnimeApiResponse>
}