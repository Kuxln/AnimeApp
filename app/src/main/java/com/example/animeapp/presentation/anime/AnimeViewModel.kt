package com.example.animeapp.presentation.anime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.AnimeApiDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeViewModel(
    private val animeApi: AnimeApiDataSource
) : ViewModel() {
    val liveData: LiveData<AnimeViewState> get() = _liveData
    private val _liveData = MutableLiveData<AnimeViewState>()
    private val animeViewState = AnimeViewState()

    init {
        animeViewState.isLoading = true
        _liveData.postValue(animeViewState)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = animeApi.getTopAnime()
                Log.d("tag", response.toString())
                animeViewState.animeTitleData = response?.data
                animeViewState.hasMoreData = response?.links?.next != null
                animeViewState.isLoading = false
                _liveData.postValue(animeViewState)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                val response = animeApi.getEpisodes(1, 0)
                Log.d("tag", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadMoreItems() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = animeApi.loadMore(animeViewState.animeTitleData.orEmpty().size)
                Log.d("tag", response.toString())
                animeViewState.animeTitleData = listOf(
                    animeViewState.animeTitleData.orEmpty(),
                    response?.data.orEmpty()
                ).flatten()
                animeViewState.hasMoreData = response?.links?.next != null
                _liveData.postValue(animeViewState)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun refreshList() {
        animeViewState.isLoading = true
        _liveData.postValue(animeViewState)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = animeApi.getTopAnime()
                Log.d("tag", response.toString())
                animeViewState.animeTitleData = response?.data
                animeViewState.hasMoreData = response?.links?.next != null
                animeViewState.isLoading = false
                _liveData.postValue(animeViewState)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}