package com.example.animeapp.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.users.AppDatabase
import com.example.animeapp.data.users.User
import com.example.animeapp.presentation.core.hashing
import com.example.animeapp.presentation.core.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    db: AppDatabase
) : ViewModel() {
    val liveData: LiveData<SignUpState> get() = _liveData
    private val _liveData = MutableLiveData<SignUpState>()

    private val signUpState = SignUpState()
    private val dao = db.userDao()

    fun onSignUpClicked(email: String, name: String, password: String) {
        val passHash = password.hashing()
        if (email.isValidEmail() && email.length >= 5) {
            if (password.length >= 8) {
                if (name.length >= 2) {
                    val user = dao.findUserByEmail(email)
                    if (user == null) {
                        dao.insert(
                            User(
                                email = email,
                                name = name,
                                password = passHash
                            )
                        )
                        signUpState.isEmailExist = false
                    } else signUpState.isEmailExist = true
                } else signUpState.isNameValid = false
            } else signUpState.isPasswordValid = false
        } else signUpState.isEmailValid = false
        signUpState.email = email
        _liveData.postValue(signUpState)
    }

    fun clearState() {
        signUpState.isEmailValid = null
        signUpState.isEmailExist = null
        signUpState.email = null
        signUpState.isPasswordValid = null
        signUpState.isNameValid = null

        _liveData.postValue(signUpState)
    }
}
