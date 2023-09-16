package com.example.animeapp.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animeapp.auth.changepassword.ChangePasswordViewModel
import com.example.animeapp.auth.signin.SignInViewModel
import com.example.animeapp.auth.signup.SignUpViewModel
import com.example.animeapp.auth.signupfinish.SignUpFinishViewModel

class MyViewModelFactory(
    private val app: AnimeApp
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel(app.db) as T
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel() as T
            modelClass.isAssignableFrom(SignUpFinishViewModel::class.java) -> SignUpFinishViewModel() as T
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> ChangePasswordViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

    }


}