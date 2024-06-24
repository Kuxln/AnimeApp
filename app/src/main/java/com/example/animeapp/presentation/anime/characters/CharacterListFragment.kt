package com.example.animeapp.presentation.anime.characters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animeapp.R
import com.example.animeapp.data.anime.AnimeCharacterResponse
import com.example.animeapp.data.anime.AnimeCharactersListResponse
import com.example.animeapp.databinding.FragmentAnimeCharactersBinding
import com.example.animeapp.domain.entity.AnimeCharacterEntity
import com.example.animeapp.presentation.core.ui.BaseFragment
import com.example.animeapp.presentation.core.ui.PaddingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterListFragment @Inject constructor() : BaseFragment<FragmentAnimeCharactersBinding>(
    R.layout.fragment_anime_characters
) {
    private val viewModel: CharacterListViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAnimeCharactersBinding.bind(view)
        val id = this.arguments?.getString("ID", "")
        id?.let { viewModel.setId(it) }


        val adapter = CharacterListAdapter(
            listOf(
                AnimeCharacterEntity("1", null, null, false),
                AnimeCharacterEntity("2", null, null, false),
                AnimeCharacterEntity("3", null, null, false),
                AnimeCharacterEntity("4", null, null, false),
                AnimeCharacterEntity("5", null, null, false),
                AnimeCharacterEntity("6", null, null, false),
                AnimeCharacterEntity("7", null, null, false),
                AnimeCharacterEntity("8", null, null, false),
                AnimeCharacterEntity("9", null, null, false),
                AnimeCharacterEntity("10", null, null, false),
                AnimeCharacterEntity("11", null, null, false),
                AnimeCharacterEntity("12", null, null, false),
                AnimeCharacterEntity("13", null, null, false),
                AnimeCharacterEntity("14", null, null, false),
                AnimeCharacterEntity("15", null, null, false),
                AnimeCharacterEntity("16", null, null, false),
                AnimeCharacterEntity("17", null, null, false),
                AnimeCharacterEntity("18", null, null, false),
                AnimeCharacterEntity("19", null, null, false),
                AnimeCharacterEntity("20", null, null, false),
            )
        )
        with(binding.recycler) {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            addItemDecoration(PaddingItemDecoration(24, 10))
            this.adapter = adapter
        }
        binding.recycler.adapter = adapter
    }


    companion object {
        fun newInstance(id: String): CharacterListFragment {
            val args = Bundle()
            args.putString("ID", id)
            val fragment = CharacterListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}