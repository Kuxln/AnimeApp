package com.example.animeapp.data.manga

data class MangaApiResponse(
    val data: List<MangaTitleData>? = null,
    val links: MangaLinks? = null
)

data class MangaTitleData(
    val id: String? = null,
    val attributes: MangaAttributes? = null
)

data class MangaLinks(
    val next: String? = null
)

data class MangaAttributes(
    val canonicalTitle: String? = null,
    val description: String? = null,
    val averageRating: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val userCount: Int? = null,
    val volumeCount: Int? = null,
    val chapterCount: Int? = null,
    val posterImage: MangaPosterImage? = null

)

data class MangaPosterImage(
    val original: String? = null
)
