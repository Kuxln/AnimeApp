package com.example.animeapp.presentation.auth

import com.example.animeapp.presentation.auth.signupfinish.SignUpFinishFragment

interface AuthorizationCallback {
    fun onSignIn()
    fun onSignUp()
    fun onResetPassword()
    fun onBackButtonPressed()
    fun onFinishSignUp(fragment: SignUpFinishFragment)
    fun onChangePassword()
    fun onAccountCreated(email: String)
}