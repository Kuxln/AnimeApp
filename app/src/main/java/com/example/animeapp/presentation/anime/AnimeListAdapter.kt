package com.example.animeapp.presentation.anime

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitleData
import com.example.animeapp.databinding.AnimeListItemBinding
import com.example.animeapp.databinding.LoadingProgressBarBinding

class AnimeListViewHolder(
    binding: AnimeListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    val canonicalTitle = binding.animeCardViewTitle
    val description = binding.animeCardViewSubTitle
    val mainImageView = binding.animeCardViewMainImageView
    val episodeCount = binding.animeCardViewAmountOfTimeTextView
    val userCount = binding.animeCardViewViews
    val averageRating = binding.animeCardViewRating
    val releaseDate = binding.animeCardReleaseDateTextView
}
class LoadingProgressBarViewHolder(
    binding: LoadingProgressBarBinding
) : RecyclerView.ViewHolder(binding.root) {
    val progressBar = binding.loadingProgressBar
}

class AnimeListAdapter(
    private val data: List<AnimeTitleData>,
    private val hasMoreData: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ITEM -> {
                val binding = AnimeListItemBinding.inflate(inflater, parent, false)
                AnimeListViewHolder(binding)
            }

            LOADING -> {
                val binding = LoadingProgressBarBinding.inflate(inflater, parent, false)
                LoadingProgressBarViewHolder(binding)
            }

            else -> throw Exception()
        }

    }

    override fun getItemId(position: Int): Long {
        return data[position].id?.toLongOrNull() ?: 0
    }
    override fun getItemCount(): Int = if (hasMoreData) { data.size + 1 } else data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                val animeListViewHolder = holder as AnimeListViewHolder
                val dataAttributes = data[position].attributes

                val userCountMetadata = if (dataAttributes?.userCount != null) {
                    "(${dataAttributes.userCount} Views)"
                } else ""

                val episodeCountMetadata = if (dataAttributes?.episodeCount != null) {
                    "${dataAttributes.episodeCount} ep."
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
                with(animeListViewHolder) {
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

            LOADING -> {
                val loadingViewHolder = holder as LoadingProgressBarViewHolder
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size ) LOADING else ITEM
    }

    companion object {
        private const val ITEM = 1
        private const val LOADING = 0
    }
}

