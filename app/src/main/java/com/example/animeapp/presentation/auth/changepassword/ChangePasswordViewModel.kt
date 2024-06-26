package com.example.animeapp.presentation.auth.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.users.AppDatabase
import com.example.animeapp.presentation.core.hashing
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    db: AppDatabase
) : ViewModel() {

    val liveData: LiveData<ChangePasswordState> get() = _liveData
    private val _liveData = MutableLiveData<ChangePasswordState>()

    private val changePasswordState = ChangePasswordState()
    private val dao = db.userDao()

    fun onResetPasswordClicked(email: String, password: String, passwordRepeated: String) {
        if (password.length >= 5 && password == passwordRepeated && email.length >= 5) {

            val user = dao.findUserByEmail(email)!!
            user.password = password.hashing()
            dao.update(user)

        } else changePasswordState.error = true
    }

}