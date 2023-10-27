package com.example.animeapp.data

import android.os.Parcel
import android.os.Parcelable

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

//todo @Parcelize
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
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(canonicalTitle)
        parcel.writeString(description)
        parcel.writeValue(episodeCount)
        parcel.writeString(averageRating)
        parcel.writeValue(userCount)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeValue(episodeLength)
        parcel.writeString(posterImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimeTitle> {
        override fun createFromParcel(parcel: Parcel): AnimeTitle {
            return AnimeTitle(parcel)
        }

        override fun newArray(size: Int): Array<AnimeTitle?> {
            return arrayOfNulls(size)
        }

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
