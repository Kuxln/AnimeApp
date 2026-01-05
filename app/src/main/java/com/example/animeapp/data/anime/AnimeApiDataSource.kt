package com.example.animeapp.data.anime

class AnimeApiDataSource(
    private val animeApi: AnimeApi
) {
    suspend fun getTopAnime(): AnimeApiResponse? {
        return animeApi.getTopAnime().body()
    }

    suspend fun getAnimeTitles(offset: Int): AnimeApiResponse? {
        return animeApi.loadNext(offset, 20).body()
    }

    suspend fun getAnimeEpisodes(id: String, offset: Int): AnimeEpisodesResponse? {
        return animeApi.getEpisodes(id, 20, offset).body()
    }

    suspend fun searchQuery(searchString: String): AnimeApiResponse? {
        return animeApi.getSearchResponse(searchString.replace(' ', '%'), 20, 0).body()
    }

    suspend fun loadMoreSearchQuery(searchString: String, offset: Int): AnimeApiResponse? {
        return animeApi.getNextSearchResponse(searchString.replace(' ', '%'), 20, offset).body()
    }

    suspend fun getTitle(id: String): AnimeTitleDataResponse? {
        return animeApi.getTitle(id).body()
    }

    suspend fun getCharacter(id: String): AnimeCharacterResponse? {
        return animeApi.getCharacter(id).body()
    }

    suspend fun getCharactersList(id: String): AnimeCharactersListResponse? {
        return animeApi.getCharactersList(id).body()
    }
}
