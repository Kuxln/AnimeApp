package com.example.animeapp.presentation.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.AppDatabase
import com.example.animeapp.presentation.core.hashing

class SignInViewModel(
    db: AppDatabase
) : ViewModel() {
    val liveData: LiveData<SignInViewState> get() = _liveData
    private val _liveData = MutableLiveData<SignInViewState>()

    private val signInViewState = SignInViewState()
    private val dao = db.userDao()

    fun onSignInClicked(email: String, password: String) {
        val user = dao.findUser(email, password.hashing())
        if (user == null) {
            signInViewState.showError = true
            _liveData.postValue(signInViewState)
        } else {
            signInViewState.showError = false
            signInViewState.email = user.email
            signInViewState.password = user.password
            signInViewState.number = user.number
            signInViewState.name = user.name
            signInViewState.gender = user.gender
            signInViewState.image = user.profileImage
            _liveData.postValue(signInViewState)
        }

    }

}