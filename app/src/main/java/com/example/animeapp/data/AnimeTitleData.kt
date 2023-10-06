package com.example.animeapp.data

data class AnimeApiResponse(
    val data: List<AnimeTitleData>
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
    val episodeLength: Int? = null
)