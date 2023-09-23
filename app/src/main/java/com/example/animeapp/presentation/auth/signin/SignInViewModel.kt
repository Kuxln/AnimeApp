package com.example.animeapp.presentation.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.AppDatabase
import com.example.animeapp.presentation.core.hashing
import com.example.animeapp.presentation.core.isValidEmail

class SignInViewModel(
    db: AppDatabase
) : ViewModel() {
    val liveData: LiveData<SignInViewState> get() = _liveData
    private val _liveData = MutableLiveData<SignInViewState>()

    private val signInViewState = SignInViewState()
    private val dao = db.userDao()

    fun onSignInClicked(email: String, password: String) {
        if (email.isValidEmail() && email.length >= 5) {
            if (password.length >= 8) {
                val user = dao.findUser(email, password.hashing())
                signInViewState.showError = user == null
            } else signInViewState.isPasswordValid = false
        } else signInViewState.isEmailValid = false
        signInViewState.email = email
        _liveData.postValue(signInViewState)
    }

    fun clearState() {
        signInViewState.isEmailValid = null
        signInViewState.email = null
        signInViewState.isPasswordValid = null
        signInViewState.showError = null
        _liveData.postValue(signInViewState)
    }
}