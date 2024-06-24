package com.example.animeapp.presentation.anime.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.animeapp.databinding.ListItemAnimeCharacterBinding
import com.example.animeapp.domain.entity.AnimeCharacterEntity

class CharacterListAdapter(
    private val dataSet: List<AnimeCharacterEntity>
) : RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAnimeCharacterBinding.inflate(inflater, parent, false)
        return CharacterListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {

    }

    class CharacterListViewHolder(
        binding: ListItemAnimeCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
//        val nameTextView = binding.
//        val imageView
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
