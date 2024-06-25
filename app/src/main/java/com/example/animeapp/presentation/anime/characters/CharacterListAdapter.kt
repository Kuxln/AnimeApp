package com.example.animeapp.presentation.anime.characters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.animeapp.R
import com.example.animeapp.databinding.ListItemAnimeCharacterBinding
import com.example.animeapp.domain.entity.AnimeCharacterEntity

class CharacterListAdapter(
    private val onLoad: () -> Unit = {}
) : RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {
    private var dataSet: List<AnimeCharacterEntity> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAnimeCharacterBinding.inflate(inflater, parent, false)
        return CharacterListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        if (dataSet.size - 1 == position) onLoad()

        holder.nameTextView.text = dataSet[position].name
        Glide.with(holder.imageView.context)
            .load(dataSet[position].image)
            .placeholder(R.drawable.anime)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)
    }

    class CharacterListViewHolder(
        binding: ListItemAnimeCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val nameTextView = binding.nameTV
        val imageView = binding.imageIV
    }

    fun updateData(newData: List<CharListPage>) {
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
}
