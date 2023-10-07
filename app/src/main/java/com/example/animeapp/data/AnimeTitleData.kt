package com.example.animeapp.data

data class AnimeApiResponse(
    val data: List<AnimeTitleData>? = null,
    val links: AnimeLinks? = null
)

data class AnimeLinks(
    val next: String? = null
)

data class AnimeTitleData(
    val id: String? = null,
    val attributes: AnimeAttributes? = null
)

data class AnimeAttributes(
    val canonicalTitle: String? = null,
    val description: String? = null,
    val episodeCount: Int? = null,
    val averageRating: String? = null,
    val userCount: Int? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val episodeLength: Int? = null,
    val posterImage: AnimePosters? = null
)

data class AnimePosters(
    val original: String? = null
)