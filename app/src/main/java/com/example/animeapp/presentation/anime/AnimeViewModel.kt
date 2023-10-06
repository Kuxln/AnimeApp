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
            val response = animeApi.getTopAnime()
            animeViewState.animeTitleData = response?.data
            animeViewState.isLoading = false
            _liveData.postValue(animeViewState)

        }
    }
}