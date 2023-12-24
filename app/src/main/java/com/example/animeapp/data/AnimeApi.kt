package com.example.animeapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {
    @GET("anime?page[limit]=20&page[offset]=0")
    suspend fun getTopAnime(): Response<AnimeApiResponse>

    @GET("anime?")
    suspend fun loadNext(
        @Query("page[offset]") offset: Int,
        @Query("page[limit]") limit: Int
    ): Response<AnimeApiResponse>

    @GET("anime/{id}/genres")
    suspend fun getGenres(@Path("id") id: Int)

    @GET("anime/{id}/categories")
    suspend fun getCategories(@Path("id") id: Int)

    @GET("anime/{id}/reviews")
    suspend fun getReviews(@Path("id") id: Int)

    @GET("anime/{id}/characters")
    suspend fun getCharacters(@Path("id") id: Int)

    @GET("anime/{id}/episodes?")
    suspend fun getEpisodes(
        @Path("id") id: String,
        @Query("page[limit]") limit: Int,
        @Query("page[offset]") offset: Int
    ): Response<AnimeEpisodesResponse>
}