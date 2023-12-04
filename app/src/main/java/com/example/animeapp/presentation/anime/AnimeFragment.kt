package com.example.animeapp.presentation.anime

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.children
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.data.AnimeTitle
import com.example.animeapp.databinding.FragmentAnimeBinding
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory
import com.example.animeapp.presentation.core.BaseFragment
import com.example.animeapp.presentation.core.PaddingItemDecoration


class AnimeFragment(
    private val onAnimeTitleSelected: (animeTitle: AnimeTitle) -> Unit = {}
) : MenuProvider, BaseFragment<FragmentAnimeBinding>(
    R.layout.fragment_anime
) {
    private val viewModel: AnimeViewModel by viewModels { AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }
    private lateinit var animeAdapter: AnimeListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this)
        binding = FragmentAnimeBinding.bind(view)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.animeToolbar)

        binding.animeRecyclerView.addItemDecoration(PaddingItemDecoration(24))
        binding.animeRecyclerView.visibility = View.INVISIBLE

        binding.animeSwipeRefreshLayout.setColorSchemeResources(R.color.orange)
        binding.animeSwipeRefreshLayout.setOnRefreshListener {
            binding.animeSwipeRefreshLayout.isRefreshing = true
            viewModel.refreshList()
        }

        animeAdapter = AnimeListAdapter(
            onScroll = {
                if (it >= 15) {
                    binding.animeArrowUp.visibility = View.VISIBLE
                } else {
                    binding.animeArrowUp.visibility = View.INVISIBLE
                }
            },
            onItemSelected = {
                if (it.attributes != null) {
                    onAnimeTitleSelected(AnimeTitle.newInstance(it.attributes))
                }
            },
            onLastElementVisible = {
                viewModel.loadMoreItems()
            }
        )
        binding.animeRecyclerView.adapter = animeAdapter
        binding.animeArrowUp.setOnClickListener {
            if (animeAdapter.itemCount > 22) {
                binding.animeRecyclerView.scrollToPosition(0)
            } else {
                binding.animeRecyclerView.smoothScrollToPosition(0)
            }
        }

        viewModel.liveData.observe(this.viewLifecycleOwner) { state ->
            state.animeTitleData?.let {
                animeAdapter.updateData(it, state.hasMoreData)
                if (binding.animeSwipeRefreshLayout.isRefreshing) {
                    binding.animeSwipeRefreshLayout.isRefreshing = false;
                }
                if (!state.isLoading) {
                    binding.animeRecyclerView.visibility = View.VISIBLE
                    binding.animeListProgressBar.visibility = View.INVISIBLE
                } else {
                    binding.animeRecyclerView.visibility = View.INVISIBLE
                    binding.animeListProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_search -> true
            else -> false
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_search_menu, menu)
        val searchItem  = menu.children.firstOrNull { it.itemId == R.id.action_search }
        val searchView = searchItem?.actionView as SearchView?
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                println("XXX: On Search text submit - $query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println("XXX: On Search text change - $newText")
                return false
            }
        })
    }

    override fun onBack(): Boolean {
        return if (parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.popBackStack()
            true
        } else false
    }
}