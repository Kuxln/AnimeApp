package com.example.animeapp.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.core.AppDatabase

class SignInViewModel(
    private val db : AppDatabase
) : ViewModel() {
    val liveData: LiveData<SignInViewState> get() = _liveData
    private val _liveData = MutableLiveData<SignInViewState>()

    private val signInViewState = SignInViewState()

    fun onSignInClicked(email: String) {
// todo(db.)
    }


}
