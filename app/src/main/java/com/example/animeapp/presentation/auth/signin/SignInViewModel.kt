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

    fun onSignInClicked(email: String, password: String) {
        if (email.isValidEmail() && email.length >= 5) {
            if (password.length >= 8) {
                val user = userRepo.findUser(email, password.hashing())
                state.showError = user == null
            } else state.isPasswordValid = false
        } else state.isEmailValid = false
        state.email = email
        _liveData.postValue(state)
    }

    fun clearState() {
        state.isEmailValid = null
        state.email = null
        state.isPasswordValid = null
        state.showError = null
        _liveData.postValue(state)
    }

    fun addPreferenceAccount() {
        with(sharedPreferences.edit()) {
            putString("email", state.email)
            apply()
        }
    }

    fun getPrefs() {
        val email = sharedPreferences.getString("email", "")
        state.pref_email = email
        _liveData.postValue(state)
    }
}