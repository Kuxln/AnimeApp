package com.example.animeapp.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    val liveData : LiveData<SignUpState> get() = _liveData
    private val _liveData = MutableLiveData<SignUpState>()

    private val signUpState = SignUpState()

    fun onSignUpClicked(email : String, name : String, password : String ) {
        signUpState.email = email
        signUpState.name = name
        signUpState.password = password
        _liveData.postValue(signUpState)

    }
}