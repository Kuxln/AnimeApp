package com.example.animeapp.presentation.manga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animeapp.data.MangaApiDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaApi: MangaApiDataSource
) : ViewModel() {
    val liveData: LiveData<MangaViewState> get() = _liveData
    private val _liveData = MutableLiveData<MangaViewState>()
    private val mangaViewState = MangaViewState()

    init {
        mangaViewState.isLoading = true
        _liveData.postValue(mangaViewState)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mangaApi.getTopManga()
                with(mangaViewState) {
                    mangaTitleData = response?.data
                    hasMoreData = response?.links?.next != null
                    isLoading = false
                }
                _liveData.postValue(mangaViewState)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun loadMoreItems() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mangaApi.loadMore(mangaViewState.mangaTitleData.orEmpty().size)
                mangaViewState.mangaTitleData = listOf(
                    mangaViewState.mangaTitleData.orEmpty(),
                    response?.data.orEmpty()
                ).flatten()
                mangaViewState.hasMoreData = response?.links?.next != null
                _liveData.postValue(mangaViewState)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun refreshList() {
        mangaViewState.isLoading = true
        _liveData.postValue(mangaViewState)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mangaApi.getTopManga()
                with(mangaViewState) {
                    mangaTitleData = response?.data
                    hasMoreData = response?.links?.next != null
                    isLoading = false
                }
                _liveData.postValue(mangaViewState)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}