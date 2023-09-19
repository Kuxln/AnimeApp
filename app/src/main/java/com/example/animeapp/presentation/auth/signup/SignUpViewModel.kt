package com.example.animeapp.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.AppDatabase
import com.example.animeapp.data.User
import com.example.animeapp.presentation.core.hashing

class SignUpViewModel(
    db : AppDatabase
) : ViewModel() {
    val liveData : LiveData<SignUpState> get() = _liveData
    private val _liveData = MutableLiveData<SignUpState>()

    private val signUpState = SignUpState()
    private val dao = db.userDao()

    fun onSignUpClicked(email : String, name : String, password : String ) {
        val passHash = password.hashing()
        dao.insert(User(
            email = email,
            name = name,
            password = passHash
        )
        )
    }
}