package com.example.animeapp.presentation.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.users.User
import com.example.animeapp.domain.UserRepository
import com.example.animeapp.presentation.core.hashing
import com.example.animeapp.presentation.core.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {
    val liveData: LiveData<SignUpState> get() = _liveData

    private val _liveData = MutableLiveData<SignUpState>()
    private val state = SignUpState()

    fun onSignUpClicked() {
        if (state.isTermsAccepted && state.isNameValid == true && state.isPasswordValid == true && state.isEmailValid == true) {
            val user = userRepo.findUserByEmail(state.email.orEmpty())
            if (user == null) {
                userRepo.insert(
                    User(
                        email = state.email.orEmpty(),
                        name = state.name,
                        password = state.password.orEmpty().hashing()
                    )
                )
            }
            state.onCreatePrefs = true
            state.isFinished = true
            _liveData.postValue(state)
        }
    }

    fun onConditionsClicked() {
        state.isTermsAccepted = !state.isTermsAccepted
    }

    fun invalidateEmail(email: String) {
        state.email = email
        state.isEmailValid = email.isValidEmail() && email.length >= 5
        _liveData.postValue(state)
    }

    fun invalidateName(name: String) {
        state.name = name
        state.isNameValid = name.length >= 2
        _liveData.postValue(state)
    }

    fun invalidatePassword(password: String) {
        state.password = password
        state.isPasswordValid = password.length >= 8
        _liveData.postValue(state)
    }
}
