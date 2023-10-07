package com.example.animeapp.presentation.anime

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitleData
import com.example.animeapp.databinding.AnimeListItemBinding

class AnimeListViewHolder(binding: AnimeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val canonicalTitle = binding.animeCardViewTitle
    val description = binding.animeCardViewSubTitle
    val mainImageView = binding.animeCardViewMainImageView
    val episodeCount = binding.animeCardViewAmountOfTimeTextView
    val userCount = binding.animeCardViewViews
    val averageRating = binding.animeCardViewRating
    val releaseDate = binding.animeCardReleaseDateTextView
}

class AnimeListAdapter(
    private val data: List<AnimeTitleData>
) : RecyclerView.Adapter<AnimeListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AnimeListItemBinding.inflate(inflater)
        return AnimeListViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id?.toLongOrNull() ?: 0
    }
    override fun getItemCount(): Int = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        val dataAttributes = data[position].attributes

        val userCountMetadata = if (dataAttributes?.userCount != null) {
            "(${dataAttributes.userCount} Views)"
        } else ""

        val episodeCountMetadata = if (dataAttributes?.episodeCount != null) {
            "${dataAttributes.episodeCount.toString()} ep."
        } else ""

        val episodeLengthMetadata = if (dataAttributes?.episodeLength != null) {
            "• ${dataAttributes.episodeLength} min"
        } else ""

        val startDateMetadata = if (dataAttributes?.startDate != null) {
            dataAttributes.startDate
        } else ""

        val endDateMetadata = if (dataAttributes?.endDate != null) {
            "-> ${dataAttributes.endDate}"
        } else "-> ongoing"
        with(holder) {
            canonicalTitle.text = dataAttributes?.canonicalTitle
            description.text = dataAttributes?.description
            averageRating.text = dataAttributes?.averageRating
            userCount.text = userCountMetadata
            episodeCount.text = "$episodeCountMetadata $episodeLengthMetadata"
            releaseDate.text = "$startDateMetadata $endDateMetadata"
            Glide.with(mainImageView.context)
                .load(dataAttributes?.posterImage?.original)
                .placeholder(R.drawable.anime)
                .into(mainImageView)
        }


    }
}

