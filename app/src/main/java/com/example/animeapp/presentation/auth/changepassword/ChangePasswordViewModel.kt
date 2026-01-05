package com.example.animeapp.presentation.auth.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.domain.UserRepository
import com.example.animeapp.presentation.core.hashing
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {
    val liveData: LiveData<ChangePasswordState> get() = _liveData
    private val _liveData = MutableLiveData<ChangePasswordState>()
    private val changePasswordState = ChangePasswordState()

    fun onResetPasswordClicked(email: String, password: String, passwordRepeated: String) {
        if (password.length >= 5 && password == passwordRepeated && email.length >= 5) {
            userRepo.findUserByEmail(email)?.let {
                it.password = password.hashing()
                userRepo.update(it)
            }
        } else changePasswordState.error = true
    }
}
