package com.example.animeapp.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animeapp.presentation.auth.changepassword.ChangePasswordViewModel
import com.example.animeapp.presentation.auth.signin.SignInViewModel
import com.example.animeapp.presentation.auth.signup.SignUpViewModel
import com.example.animeapp.presentation.auth.signupfinish.SignUpFinishViewModel

class AppViewModelFactory(
    private val app: AnimeApp
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel(app.db) as T
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(app.db) as T
            modelClass.isAssignableFrom(SignUpFinishViewModel::class.java) -> SignUpFinishViewModel(app.db) as T
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> ChangePasswordViewModel(app.db) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}