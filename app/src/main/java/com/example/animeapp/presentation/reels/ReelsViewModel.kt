package com.example.animeapp.presentation.reels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReelsViewModel : ViewModel(){
    val liveData : LiveData<ReelsViewState> get() = _liveData
    private val _liveData = MutableLiveData<ReelsViewState>()

    val reelsViewState = ReelsViewState()


}