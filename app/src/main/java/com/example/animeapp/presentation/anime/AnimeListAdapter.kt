package com.example.animeapp.presentation.anime

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.data.AnimeTitleData
import com.example.animeapp.databinding.AnimeListItemBinding

class AnimeListViewHolder(binding: AnimeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val canonicalTitle = binding.animeCardViewTitle
    val description = binding.animeCardViewSubTitle
    val image = binding.animeCardViewMainImageView
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
        val userCountMetadata = if (data[position].attributes?.userCount != null) {
            "(${data[position].attributes?.userCount} Views)"
        } else ""

        val episodeCountMetadata = if (data[position].attributes?.episodeCount != null) {
            "${data[position].attributes?.episodeCount.toString()} ep."
        } else ""

        val episodeLengthMetadata = if (data[position].attributes?.episodeLength != null) {
            "• ${data[position].attributes?.episodeLength} min"
        } else ""

        val startDateMetadata = if (data[position].attributes?.startDate != null) {
            data[position].attributes?.startDate
        } else ""

        val endDateMetadata = if (data[position].attributes?.endDate != null) {
            "-> ${data[position].attributes?.endDate}"
        } else "-> ongoing"
        holder.canonicalTitle.text = data[position].attributes?.canonicalTitle
        holder.description.text = data[position].attributes?.description
        holder.averageRating.text = data[position].attributes?.averageRating
        holder.userCount.text = userCountMetadata
        holder.episodeCount.text = "$episodeCountMetadata $episodeLengthMetadata"
        holder.releaseDate.text = "$startDateMetadata $endDateMetadata"
//        holder.image.setImageURI(data[position].image?.toUri() ?: )
    }
}

