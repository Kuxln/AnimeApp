package com.example.animeapp.presentation.manga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MangaViewModel : ViewModel(){
    val liveData : LiveData<MangaViewState> get() = _liveData
    private val _liveData = MutableLiveData<MangaViewState>()

    val mangaViewState = MangaViewState()


}