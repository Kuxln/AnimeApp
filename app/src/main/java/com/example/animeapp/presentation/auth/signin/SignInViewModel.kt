package com.example.animeapp.presentation.auth.signin

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.R
import com.example.animeapp.data.users.AppDatabase
import com.example.animeapp.presentation.core.hashing
import com.example.animeapp.presentation.core.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    db: AppDatabase,
    @ApplicationContext context: Context
) : ViewModel() {
    val liveData: LiveData<SignInViewState> get() = _liveData
    private val _liveData = MutableLiveData<SignInViewState>()

    private val signInViewState = SignInViewState()
    private val dao = db.userDao()

    val sharedPreferences = context.getSharedPreferences(R.string.pref_acc_key.toString(), Context.MODE_PRIVATE)

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

    fun addPreferenceAccount() {
        with(sharedPreferences.edit()) {
            putString("email", signInViewState.email)
            apply()
        }
    }

    fun getPrefs() {
        val email = sharedPreferences.getString("email", "")
        signInViewState.pref_email = email
        _liveData.postValue(signInViewState)
    }
}