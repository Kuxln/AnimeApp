package com.example.animeapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaApi {
    @GET("manga?page[limit]=20&page[offset]=0")
    suspend fun getTopManga() : Response<MangaApiResponse>

    @GET("anime?")
    suspend fun loadNext(@Query("page[offset]") offset: Int, @Query("page[limit]") limit : Int) : Response<MangaApiResponse>
}