package com.example.animeapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel(){
    val liveData : LiveData<SearchViewState> get() = _liveData
    private val _liveData = MutableLiveData<SearchViewState>()

    val searchViewState = SearchViewState()


}