package com.example.animeapp.domain.entity

data class AnimeTitleEntity (
    val id: String,
    val canonicalTitle: String = "",
    val description: String = "",
    val episodeCount: Int = 0,
    val averageRating: String = "",
    val userCount: Int = 0,
    val startDate: String = "",
    val endDate: String = "",
    val episodeLength: Int = 0,
    val image: String = "",
)