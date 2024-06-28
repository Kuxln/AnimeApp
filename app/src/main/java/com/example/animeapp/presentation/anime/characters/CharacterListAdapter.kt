package com.example.animeapp.presentation.anime.characters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.LayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.animeapp.R
import com.example.animeapp.databinding.ListItemAnimeCharacterBinding
import com.example.animeapp.databinding.ListLoadingProgressBarBinding
import com.example.animeapp.domain.entity.AnimeCharacterEntity


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
                val nameMetadata = dataSet[position].name?.split(" ").orEmpty()
                when (nameMetadata.size) {
                    1 -> viewHolder.nameTextView1.text = nameMetadata[0]
                    2 -> {
                        viewHolder.nameTextView1.text = nameMetadata[0]
                        val layoutParams1 = viewHolder.nameTextView1.layoutParams as LayoutParams
                        layoutParams1.topMargin = 30
                        layoutParams1.bottomMargin = -15

                        viewHolder.nameTextView2.text = nameMetadata[1]
                        val layoutParams2 = viewHolder.nameTextView2.layoutParams as LayoutParams
                        layoutParams2.bottomMargin = 30
                        viewHolder.nameTextView2.visibility = View.VISIBLE
                    }

                    3 -> {
                        viewHolder.nameTextView1.text = nameMetadata[0]
                        val layoutParams1 = viewHolder.nameTextView1.layoutParams as LayoutParams
                        layoutParams1.topMargin = 30
                        layoutParams1.bottomMargin = -15
                        viewHolder.nameTextView2.text = nameMetadata[1]
                        val layoutParams2 = viewHolder.nameTextView2.layoutParams as LayoutParams
                        layoutParams2.topMargin = -10
                        layoutParams2.bottomMargin = -15
                        viewHolder.nameTextView2.visibility = View.VISIBLE
                        viewHolder.nameTextView3.text = nameMetadata[2]
                        val layoutParams3 = viewHolder.nameTextView3.layoutParams as LayoutParams
                        layoutParams3.bottomMargin = 30
                        viewHolder.nameTextView3.visibility = View.VISIBLE
                    }

                    else -> {
                        viewHolder.nameTextView1.text = nameMetadata[0]
                        val layoutParams1 = viewHolder.nameTextView1.layoutParams as LayoutParams
                        layoutParams1.topMargin = 30
                        layoutParams1.bottomMargin = -15
                        viewHolder.nameTextView2.text = nameMetadata[1]
                        val layoutParams2 = viewHolder.nameTextView2.layoutParams as LayoutParams
                        layoutParams2.topMargin = -10
                        layoutParams2.bottomMargin = -15
                        viewHolder.nameTextView2.visibility = View.VISIBLE
                        viewHolder.nameTextView3.text = nameMetadata[2]
                        val layoutParams3 = viewHolder.nameTextView3.layoutParams as LayoutParams
                        layoutParams3.bottomMargin = 30
                        viewHolder.nameTextView3.visibility = View.VISIBLE
                    }
                }

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
        val nameTextView1 = binding.nameTV1
        val nameTextView2 = binding.nameTV2
        val nameTextView3 = binding.nameTV3
        val imageView = binding.imageIV
    }

    class ProgressBarViewHolder(
        binding: ListLoadingProgressBarBinding
    ) : RecyclerView.ViewHolder(binding.root)

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
