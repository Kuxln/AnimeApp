package com.example.animeapp.presentation.anime.characters

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

        val adapter = CharacterListAdapter { viewModel.onLoad() }

        with(binding.recycler) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(PaddingItemDecoration(24, 10))
            this.adapter = adapter
        }
        binding.recycler.adapter = adapter

        viewModel.livedata.observe(this.viewLifecycleOwner) { state ->
            state.pageList?.let {
                adapter.updateData(it)
            }
            if (state.isLoading == true) {
                binding.progressBar.visibility = View.VISIBLE
                binding.recycler.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                binding.recycler.visibility = View.VISIBLE
            }
//            binding.
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