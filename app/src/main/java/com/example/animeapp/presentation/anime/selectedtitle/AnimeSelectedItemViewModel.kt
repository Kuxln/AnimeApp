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
class AnimeSelectedItemViewModel @Inject constructor(
    private val animeApi: AnimeApiDataSource
) : ViewModel() {
    val liveData: LiveData<AnimeSelectedItemViewState> get() = _livedata
    private val _livedata = MutableLiveData<AnimeSelectedItemViewState>()
    private val state = AnimeSelectedItemViewState()

    fun setId(id: String?) {
        state.isLoading = true
        state.id = id
        _livedata.postValue(state)
        this.fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = state.id?.let { animeApi.getTitle(it) }
                response?.let {
                    state.title = it
                    state.isLoading = false
                    _livedata.postValue(state)
                }

                Log.d("aaaaaaaaa", state?.title?.data?.attributes?.description!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
