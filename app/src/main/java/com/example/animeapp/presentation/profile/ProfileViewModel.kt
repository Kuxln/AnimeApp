package com.example.animeapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel(){
    val liveData : LiveData<ProfileViewState> get() = _liveData
    private val _liveData = MutableLiveData<ProfileViewState>()

    val profileViewState = ProfileViewState()


}