package com.example.animeapp.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.core.AppDatabase
import java.security.MessageDigest

class SignInViewModel(
    db : AppDatabase
) : ViewModel() {
    val liveData: LiveData<SignInViewState> get() = _liveData
    private val _liveData = MutableLiveData<SignInViewState>()

    private val signInViewState = SignInViewState()
    private val dao = db.userDao()

    fun onSignInClicked(email: String, password : String) {
        val user = dao.findUser(email, hashing(password))
        if( user == null) {
            signInViewState.showError = true
            _liveData.postValue(signInViewState)
        }

    }

    private fun hashing(password: String) : String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }


    }

}