package com.example.animeapp.presentation.anime.characters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.data.anime.AnimeCharacterResponse
import com.example.animeapp.data.anime.AnimeCharactersListResponse
import com.example.animeapp.databinding.FragmentAnimeCharactersBinding
import com.example.animeapp.presentation.core.ui.BaseFragment
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

        viewModel.livedata.observe(this.viewLifecycleOwner) {state ->
            state.charactersList.let {
                for (a: AnimeCharacterResponse in state.charactersList) {
                    a.data?.attributes?.canonicalName?.let { it1 -> Log.d("aaaa", it1) }
                }
            }


        }
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