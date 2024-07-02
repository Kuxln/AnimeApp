package com.example.animeapp.presentation.anime.anime_tab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.animeapp.R
import com.example.animeapp.data.anime.AnimeTitleData
import com.example.animeapp.databinding.ListItemAnimeBinding
import com.example.animeapp.databinding.ListLoadingProgressBarBinding
import com.example.animeapp.domain.entity.AnimeTitleEntity
import com.example.animeapp.presentation.core.ui.LoadingProgressBarViewHolder

class AnimeDiffUtil(
    private val oldData: List<AnimeTitleEntity>,
    private val newData: List<AnimeTitleEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].canonicalTitle == newData[newItemPosition].canonicalTitle
    }
}

class AnimeListViewHolder(
    binding: ListItemAnimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    val canonicalTitle = binding.animeCardViewTitle
    val description = binding.animeCardViewSubTitle
    val mainImageView = binding.animeCardViewMainImageView
    val episodeCount = binding.animeCardViewAmountOfTimeTextView
    val userCount = binding.animeCardViewViews
    val averageRating = binding.animeCardViewRating
    val releaseDate = binding.animeCardReleaseDateTextView
    val cardView = binding.animeCardView
}

class AnimeListAdapter(
    private val onScroll: (visiblePosition: Int) -> Unit = {},
    private val onLastElementVisible: () -> Unit = {},
    private val onItemSelected: (animeTitleData: AnimeTitleEntity) -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var data: List<AnimeTitleEntity> = listOf()
    private var hasMoreData: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM -> {
                val binding = ListItemAnimeBinding.inflate(inflater, parent, false)
                AnimeListViewHolder(binding)
            }

            LOADING -> {
                val binding = ListLoadingProgressBarBinding.inflate(inflater, parent, false)
                LoadingProgressBarViewHolder(binding)
            }

            else -> throw Exception()
        }

    }

    override fun getItemId(position: Int): Long {
        return data[position].id.toLongOrNull() ?: 0
    }

    override fun getItemCount(): Int = if (hasMoreData) {
        data.size + 1
    } else data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onScroll(position)

        when (getItemViewType(position)) {
            ITEM -> {
                val animeListViewHolder = holder as AnimeListViewHolder
                animeListViewHolder.cardView.setOnClickListener {
                    onItemSelected(data[position])
                }

                val dataAttributes = data[position]

                val userCountMetadata = if (dataAttributes.userCount > 5000)
                    "(" + (dataAttributes.userCount / 1000).toString() + "k+ Views)"
                else "(${dataAttributes.userCount} Views)"

                val episodeCountMetadata = "${dataAttributes.episodeCount} ep."

                val episodeLengthMetadata = "• ${dataAttributes.episodeLength} min"

                val animeStartDate = dataAttributes.startDate

                val endDateMetadata = "-> ${dataAttributes.endDate}"

                val amountOfTimeMD = "$episodeCountMetadata $episodeLengthMetadata"
                val releaseDateMD = "$animeStartDate $endDateMetadata"
                with(animeListViewHolder) {
                    canonicalTitle.text = dataAttributes.canonicalTitle
                    description.text = dataAttributes.description
                    averageRating.text = dataAttributes.averageRating
                    userCount.text = userCountMetadata
                    episodeCount.text = amountOfTimeMD
                    releaseDate.text = releaseDateMD
                    Glide.with(mainImageView.context)
                        .load(dataAttributes.image)
                        .placeholder(R.drawable.anime)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mainImageView)
                }
            }

            LOADING -> {
                onLastElementVisible()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) LOADING else ITEM
    }

    fun updateData(newData: List<AnimeTitleEntity>, hasMore: Boolean, isSearching: Boolean) {
        val animeDiffUtil = AnimeDiffUtil(this.data, newData)
        val animeDiffResult = DiffUtil.calculateDiff(animeDiffUtil)
        val newDataSet: List<AnimeTitleEntity> = if (isSearching) {
            this.data + newData
        } else {
            newData
        }

        this.data = newDataSet
        this.hasMoreData = hasMore
        animeDiffResult.dispatchUpdatesTo(this)
    }

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }
}

