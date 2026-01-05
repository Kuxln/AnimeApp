package com.example.animeapp.presentation.reels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReelsViewModel @Inject constructor() : ViewModel() {
    val liveData: LiveData<ReelsViewState> get() = _liveData
    private val _liveData = MutableLiveData<ReelsViewState>()

    val reelsViewState = ReelsViewState()
}
