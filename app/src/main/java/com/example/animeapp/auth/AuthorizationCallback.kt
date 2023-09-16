package com.example.animeapp.auth

interface AuthorizationCallback {
    fun onSignIn()
    fun onSignUp()
    fun onResetPassword()
    fun onBackButtonPressed()
    fun onFinishSignUp()
    fun onChangePassword()
}