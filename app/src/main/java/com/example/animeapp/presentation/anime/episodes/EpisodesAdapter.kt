package com.example.animeapp.presentation.anime.episodes

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.animeapp.R
import com.example.animeapp.data.anime.AnimeEpisodesData
import com.example.animeapp.databinding.ListItemAnimeEpisodeBinding
import com.example.animeapp.databinding.ListLoadingProgressBarBinding
import com.example.animeapp.presentation.core.ui.LoadingProgressBarViewHolder

class AnimeSelectedItemEpisodesAdapter(
    private val onLastElementVisible: () -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var data: MutableList<AnimeEpisodesData> = mutableListOf()
    private var hasMoreData: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM -> {
                val binding = ListItemAnimeEpisodeBinding.inflate(inflater, parent, false)
                AnimeEpisodeItemViewHolder(binding)
            }

            LOADING -> {
                val binding = ListLoadingProgressBarBinding.inflate(inflater, parent, false)
                LoadingProgressBarViewHolder(binding)
            }

            else -> throw Exception()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                val itemViewHolder = holder as AnimeEpisodeItemViewHolder
                val attributes = data[position].attributes
                val seasonEpisodeMetadata =
                    if (attributes?.seasonNumber != null && attributes.number != null)
                        "S${attributes.seasonNumber}, EP${attributes.number}"
                    else ""
                val lengthMetadata = "${attributes?.length.toString()} min"

                with(itemViewHolder) {
                    title.text = attributes?.canonicalTitle
                    Glide.with(epImage.context)
                        .load(attributes?.thumbnail?.original)
                        .placeholder(R.drawable.anime)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(epImage)

                    description.text = attributes?.description
                    seasonAndEpisode.text =seasonEpisodeMetadata
                        length.text = lengthMetadata
                }
            }

            LOADING -> onLastElementVisible()
        }
    }

    override fun getItemCount(): Int = if (hasMoreData) data.size + 1 else data.size

    override fun getItemViewType(position: Int): Int = if (position == data.size) LOADING else ITEM

    fun updateData(newData: List<AnimeEpisodesData>, hasMore: Boolean) {
        if (!hasMore) notifyItemRemoved(this.data.size)
        val animeDiffUtil = AnimeEpisodesDiffUtil(this.data, newData)
        val animeDiffResult = DiffUtil.calculateDiff(animeDiffUtil)
        this.data.clear()
        this.data.addAll(newData)
        this.hasMoreData = hasMore
        Log.d("tag", hasMore.toString())
        Log.d("tag", itemCount.toString())
        animeDiffResult.dispatchUpdatesTo(this)

    }

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }
}

class AnimeEpisodeItemViewHolder(
    binding: ListItemAnimeEpisodeBinding
) : RecyclerView.ViewHolder(binding.root) {
    val epImage = binding.epImage
    val title = binding.titleTextView
    val description = binding.descriptionTextView
    val length = binding.lengthTextView
    val seasonAndEpisode = binding.seasonEpisodeTextView
}

class AnimeEpisodesDiffUtil(
    private val oldData: List<AnimeEpisodesData>,
    private val newData: List<AnimeEpisodesData>
) : DiffUtil.Callback() {
    init {
        oldData
    }

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
        return oldData[oldItemPosition].attributes?.canonicalTitle == newData[newItemPosition].attributes?.canonicalTitle
    }
}