package com.example.animeapp.auth

interface AuthorizationCallback {
    fun onSignInClicked()
    fun onSignUpClicked()
    fun onResetPassword()
    fun onBackButtonPressed()
    fun onFinishSignUpClicked()
    fun onCreateNewPassword()
}