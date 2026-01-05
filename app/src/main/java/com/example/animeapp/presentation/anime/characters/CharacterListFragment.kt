package com.example.animeapp.presentation.anime.characters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentAnimeCharactersBinding
import com.example.animeapp.presentation.core.ui.BaseFragment
import com.example.animeapp.presentation.core.ui.PaddingItemDecoration
import com.example.animeapp.presentation.core.ui.StaggeredGridLayoutManagerWrapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterListFragment @Inject constructor() : BaseFragment<FragmentAnimeCharactersBinding>(
    R.layout.fragment_anime_characters
) {
    private val viewModel: CharacterListViewModel by viewModels()
    private lateinit var state: CharacterListViewState
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAnimeCharactersBinding.bind(view)
        val id = this.arguments?.getString("ID", "")
        id?.let { viewModel.setId(it) }

        val adapter = CharacterListAdapter(onLoad = { viewModel.onLoad() })

        binding.recycler.layoutManager =
            StaggeredGridLayoutManagerWrapper(
                spanCount = 2,
                orientation = StaggeredGridLayoutManager.VERTICAL,
            )
        binding.recycler.addItemDecoration(
            PaddingItemDecoration(
                sizeVertical = 24,
                sizeHorizontal = 10
            )
        )
        binding.recycler.adapter = adapter

        viewModel.livedata.observe(this.viewLifecycleOwner) { state ->
            this.state = state
            state.pageList?.let {
                adapter.updateData(it, state.hasMore)
            }
            if (state.isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.recycler.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                binding.recycler.visibility = View.VISIBLE
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
