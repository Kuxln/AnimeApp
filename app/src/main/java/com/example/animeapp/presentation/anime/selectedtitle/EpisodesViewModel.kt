package com.example.animeapp.presentation.anime.selectedtitle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.anime.AnimeApiDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val animeApi: AnimeApiDataSource
) : ViewModel() {
    val liveData: LiveData<EpisodesViewState> get() = _liveData
    private val _liveData = MutableLiveData<EpisodesViewState>()
    private val viewState = EpisodesViewState()
    private var id: String? = null


    fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = id?.let { animeApi.getAnimeEpisodes(it, viewState.episodesData.orEmpty().size)}
                Log.d("tag", response.toString())
                response?.let {
                    viewState.episodesData = listOf(
                        viewState.episodesData.orEmpty(),
                        it.data.orEmpty()
                    ).flatten()
                    viewState.hasMore = it.links?.next != null
                    _liveData.postValue(viewState)
                }

            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun populateData(id: String) {
        this.id = id
        viewState.isLoading = true
        _liveData.postValue(viewState)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = id?.let { animeApi.getAnimeEpisodes(it, 0) }
                Log.d("tag", response.toString())
                response?.let {
                    viewState.episodesData = it.data
                    viewState.hasMore = it.links?.next != null
                    viewState.isLoading = false
                    _liveData.postValue(viewState)
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }


}