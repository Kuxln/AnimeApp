package com.example.animeapp.data.anime

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {
    @GET("anime?page[limit]=20&page[offset]=0&sort=popularityRank")
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

    @GET("anime/{id}/")
    suspend fun getTitle(@Path("id") id: String) : Response<AnimeTitleDataResponse>

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

    @GET("anime?")
    suspend fun getSearchResponse(
        @Query("filter[text]") searchString: String,
        @Query("page[limit]") limit: Int,
        @Query("page[offset]") offset: Int
    ): Response<AnimeApiResponse>

    @GET("anime?")
    suspend fun getNextSearchResponse(
        @Query("filter[text]") searchString: String,
        @Query("page[limit]") limit: Int,
        @Query("page[offset]") offset: Int
    ): Response<AnimeApiResponse>

    @GET("anime/{id}/relationships/characters")
    suspend fun getCharactersList(
        @Path("id") id: String,
    ): Response<AnimeCharactersListResponse>

    @GET("media-characters/{id}/character")
    suspend fun getCharacter(
        @Path("id") id: String,
    ): Response<AnimeCharacterResponse>
}