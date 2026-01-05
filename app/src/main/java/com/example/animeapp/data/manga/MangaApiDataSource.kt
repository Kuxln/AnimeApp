package com.example.animeapp.data.manga

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
