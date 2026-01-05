package com.example.animeapp.presentation.anime.anime_tab

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.domain.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repo: AnimeRepository
) : ViewModel() {
    val liveData: LiveData<AnimeViewState> get() = _liveData
    private val _liveData = MutableLiveData<AnimeViewState>()
    private val viewState = AnimeViewState()

    init {
        viewState.isLoading = true
        _liveData.postValue(viewState)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getTopAnime()
                Log.d("tag", response.toString())
                viewState.animeTitleData = response.data
                viewState.hasMoreData = response.hasNext
                viewState.isLoading = false
                viewState.setData = true
                _liveData.postValue(viewState)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onLoadMore() {
        if (viewState.isSearching) loadMoreSearch()
        if (viewState.setData) loadMore()
    }

    private fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.getAnimeTitles(viewState.animeTitleData.orEmpty().size)
                Log.d("tag", response.toString())
                viewState.animeTitleData = listOf(
                    viewState.animeTitleData.orEmpty(),
                    response.data
                ).flatten()
                viewState.hasMoreData = response.hasNext
                viewState.isSearching = false
                _liveData.postValue(viewState)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadMoreSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.loadMoreSearchQuery(
                    viewState.searchString,
                    viewState.animeTitleData.orEmpty().size
                )
                Log.d("tag", response.toString())
                viewState.animeTitleData = listOf(
                    viewState.animeTitleData.orEmpty(),
                    response.data
                ).flatten()
                viewState.hasMoreData = response.hasNext
                _liveData.postValue(viewState)
            } catch (e: Exception) {
                e.printStackTrace()
                viewState.isLoading = false
                _liveData.postValue(viewState)
            }
        }
    }

    fun setSearchString(searchString: String) {
        if (viewState.searchString == searchString && viewState.animeSearchTitleData != null) {
            viewState.setSearchData = true
            _liveData.postValue(viewState)
            return
        }
        viewState.searchString = searchString
        _liveData.postValue(viewState)
        onSearch()
    }

    private fun onSearch() {
        viewState.setData = false
        viewState.isLoading = true
        _liveData.postValue(viewState)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repo.searchQuery(viewState.searchString)
                Log.d("tag", response.toString())
                viewState.animeSearchTitleData = response.data
                viewState.searchHasMoreData = response.hasNext
                viewState.isLoading = false
                viewState.isSearching = viewState.searchHasMoreData
                viewState.setSearchData = true
                _liveData.postValue(viewState)
            } catch (e: Exception) {
                e.printStackTrace()
                viewState.isLoading = false
                _liveData.postValue(viewState)
            }
        }
    }

    fun onRefresh() {
        viewState.setData = false
        viewState.setSearchData = false
        viewState.isSearching = false
        viewState.isLoading = true
        _liveData.postValue(viewState)
        if (viewState.animeTitleData != null) {
            viewState.isLoading = false
            viewState.setData = true
            _liveData.postValue(viewState)
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repo.getTopAnime()
                    Log.d("tag", response.toString())
                    viewState.animeTitleData = response.data
                    viewState.hasMoreData = response.hasNext
                    viewState.isLoading = false
                    viewState.setData = true
                    _liveData.postValue(viewState)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
