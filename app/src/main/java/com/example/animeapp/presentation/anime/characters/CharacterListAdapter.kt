package com.example.animeapp.presentation.anime.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.animeapp.R
import com.example.animeapp.databinding.ListItemAnimeCharacterBinding
import com.example.animeapp.databinding.ListLoadingProgressBarBinding
import com.example.animeapp.domain.entity.AnimeCharacterEntity
import com.example.animeapp.presentation.anime.anime_tab.AnimeListAdapter

class CharacterListAdapter(
    private val onLoad: () -> Unit = {}
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataSet: List<AnimeCharacterEntity> = listOf()
    private var hasMore: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM -> CharacterListViewHolder(
                ListItemAnimeCharacterBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            PROGRESS_BAR -> ProgressBarViewHolder(
                ListLoadingProgressBarBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )

            else -> throw Exception()
        }
    }

    override fun getItemCount(): Int {
        return if (hasMore) dataSet.size + 1 else dataSet.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                val viewHolder = holder as CharacterListViewHolder
                viewHolder.nameTextView.text = dataSet[position].name
                Glide.with(viewHolder.imageView.context)
                    .load(dataSet[position].image)
                    .placeholder(R.drawable.anime)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.imageView)
            }

            PROGRESS_BAR -> {
                onLoad()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataSet.size) PROGRESS_BAR else ITEM
    }

    class CharacterListViewHolder(
        binding: ListItemAnimeCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val nameTextView = binding.nameTV
        val imageView = binding.imageIV
    }

    class ProgressBarViewHolder(
        binding: ListLoadingProgressBarBinding
    ) : RecyclerView.ViewHolder(binding.root) {}

    fun updateData(newData: List<CharListPage>, hasMore: Boolean) {
        this.hasMore = hasMore
        val newDataSet =
            buildList {
                newData.forEach { addAll(it.charList) }
            }
        val diffUtil = CharacterListDiffUtil(dataSet, newDataSet)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        this.dataSet = newDataSet
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private class CharacterListDiffUtil(
        private val oldData: List<AnimeCharacterEntity>,
        private val newData: List<AnimeCharacterEntity>
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
            return oldData[oldItemPosition].name == newData[newItemPosition].name
        }
    }

    companion object {
        const val ITEM = 1
        const val PROGRESS_BAR = 0
    }
}
