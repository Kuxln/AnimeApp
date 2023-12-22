package com.example.animeapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AnimeApiResponse(
    val data: List<AnimeTitleData>? = null,
    val links: AnimeLinks? = null
)

data class AnimeLinks(
    val next: String? = null
)

@Parcelize
data class AnimeTitleData(
    val id: String? = null,
    val attributes: AnimeAttributes? = null
): Parcelable

@Parcelize
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
): Parcelable

@Parcelize
data class AnimePosters(
    val original: String? = null
):Parcelable

@Parcelize
data class AnimeTitle(
    val canonicalTitle: String? = null,
    val description: String? = null,
    val episodeCount: Int? = null,
    val averageRating: String? = null,
    val userCount: Int? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val episodeLength: Int? = null,
    val posterImage: String? = null
) : Parcelable {
    companion object {
        fun newInstance(animeAttributes: AnimeAttributes): AnimeTitle {
            return AnimeTitle(
                animeAttributes.canonicalTitle,
                animeAttributes.description,
                animeAttributes.episodeCount,
                animeAttributes.averageRating,
                animeAttributes.userCount,
                animeAttributes.startDate,
                animeAttributes.endDate,
                animeAttributes.episodeLength,
                animeAttributes.posterImage?.original
            )
        }
    }
}

data class AnimeEpisodesResponse (
    val data: List<AnimeEpisodesData>? = null,
    val links: AnimeEpisodesLinks? = null
)

data class AnimeEpisodesLinks (
    val next: String? = null
)

data class AnimeEpisodesData (
    val id: String? = null
    val attributes: AnimeEpisodesAttributes? = null
)

data class AnimeEpisodesAttributes (
    val seasonNumber: Int? = null,
    val number: Int? = null,
    val length: Int? = null,
    val titles: AnimeEpisodeCanonicalTitle? = null,
    val description: String? = null,
    val thumbnail: AnimeEpisodeThumbnail? = null
)

data class AnimeEpisodeCanonicalTitle (
    val canonicalTitle: String? = null
)

data class AnimeEpisodeThumbnail (
    val original: String? = null
)