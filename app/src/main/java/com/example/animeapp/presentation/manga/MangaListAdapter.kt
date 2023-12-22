package com.example.animeapp.presentation.manga

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.animeapp.R
import com.example.animeapp.data.MangaTitleData
import com.example.animeapp.databinding.ListItemAnimeBinding
import com.example.animeapp.databinding.ListLoadingProgressBarBinding
import com.example.animeapp.presentation.core.ui.LoadingProgressBarViewHolder

class MangaDiffUtil(
    private val oldData: List<MangaTitleData>,
    private val newData: List<MangaTitleData>
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
        return oldData[oldItemPosition].attributes?.hashCode() == newData[newItemPosition].attributes?.hashCode()
    }
}

class MangaListViewHolder(
    binding: ListItemAnimeBinding
) : RecyclerView.ViewHolder(binding.root) {
    val canonicalTitle = binding.animeCardViewTitle
    val description = binding.animeCardViewSubTitle
    val mainImageView = binding.animeCardViewMainImageView
    val episodeCount = binding.animeCardViewAmountOfTimeTextView
    val userCount = binding.animeCardViewViews
    val averageRating = binding.animeCardViewRating
    val releaseDate = binding.animeCardReleaseDateTextView
}

class MangaListAdapter(
    private val onUpArrowShow: () -> Unit = {},
    private val onArrowHide: () -> Unit = {},
    private val onLastElementVisible: () -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var data: MutableList<MangaTitleData> = mutableListOf()
    private var hasMoreData: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ITEM -> {
                val binding = ListItemAnimeBinding.inflate(inflater, parent, false)
                MangaListViewHolder(binding)
            }

            LOADING -> {
                val binding = ListLoadingProgressBarBinding.inflate(inflater, parent, false)
                LoadingProgressBarViewHolder(binding)
            }

            else -> throw Exception()
        }
    }

    override fun getItemId(position: Int): Long {
        return data[position].id?.toLongOrNull() ?: 0
    }

    override fun getItemCount(): Int = if (hasMoreData) {
        data.size + 1
    } else data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= 40) {
            onUpArrowShow()
        } else onArrowHide()
        when (getItemViewType(position)) {
            ITEM -> {
                val mangaListViewHolder = holder as MangaListViewHolder
                val dataAttributes = data[position].attributes

                val userCountMetadata = if (dataAttributes?.userCount != null) {
                    "(${dataAttributes.userCount} Views)"
                } else ""

                val episodeCountMetadata = if (dataAttributes?.chapterCount != null) {
                    "${dataAttributes.chapterCount} ch."
                } else ""

                val episodeLengthMetadata = if (dataAttributes?.volumeCount != null) {
                    "• ${dataAttributes.volumeCount} volumes"
                } else ""

                val startDateMetadata = if (dataAttributes?.startDate != null) {
                    dataAttributes.startDate
                } else ""

                val endDateMetadata = if (dataAttributes?.endDate != null) {
                    "-> ${dataAttributes.endDate}"
                } else "-> ongoing"

                val avgRating = if (dataAttributes?.averageRating != null) {
                    dataAttributes.averageRating
                } else "unknown"
                with(mangaListViewHolder) {
                    canonicalTitle.text = dataAttributes?.canonicalTitle
                    description.text = dataAttributes?.description
                    averageRating.text = avgRating
                    userCount.text = userCountMetadata
                    episodeCount.text = "$episodeCountMetadata $episodeLengthMetadata"
                    releaseDate.text = "$startDateMetadata $endDateMetadata"
                    Glide.with(mainImageView.context)
                        .load(dataAttributes?.posterImage?.original)
                        .placeholder(R.drawable.anime)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mainImageView)
                }
            }

            LOADING -> {
                val loadingViewHolder = holder as LoadingProgressBarViewHolder
                onLastElementVisible()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) LOADING else ITEM
    }

    fun updateData(newData: List<MangaTitleData>, hasMore: Boolean) {
        val mangaDiffUtil = MangaDiffUtil(this.data, newData)
        val mangaDiffResult = DiffUtil.calculateDiff(mangaDiffUtil)
        this.data.clear()
        this.data.addAll(newData)
        this.hasMoreData = hasMore
        mangaDiffResult.dispatchUpdatesTo(this)
    }

    companion object {
        private const val ITEM = 1
        private const val LOADING = 0
    }
}