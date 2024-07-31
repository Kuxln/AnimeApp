package com.example.animeapp.presentation.auth.signin

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.R
import com.example.animeapp.domain.UserRepository
import com.example.animeapp.presentation.core.hashing
import com.example.animeapp.presentation.core.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepo: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val liveData: LiveData<SignInViewState> get() = _liveData
    private val _liveData = MutableLiveData<SignInViewState>()
    private val state = SignInViewState()
    private val sharedPreferences =
        context.getSharedPreferences(R.string.pref_acc_key.toString(), Context.MODE_PRIVATE)

    fun onSignInClicked() {
        if (state.isEmailValid == true && state.isPasswordValid == true) {
            val user = userRepo.findUser(state.email.orEmpty(), state.password.orEmpty().hashing())
            state.success = user != null
            _liveData.postValue(state)
        }
    }

    fun addPreferenceAccount() {
        with(sharedPreferences.edit()) {
            putString("email", state.email)
            apply()
        }
    }

    fun invalidateEmail(email: String) {
        state.email = email
        state.isEmailValid = email.isValidEmail() && email.length >= 5
        _liveData.postValue(state)
    }

    fun invalidatePassword(password: String) {
        state.password = password
        state.isPasswordValid = password.length >= 8
        _liveData.postValue(state)
    }
}