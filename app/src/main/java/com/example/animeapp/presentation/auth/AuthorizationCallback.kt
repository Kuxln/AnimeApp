package com.example.animeapp.presentation.auth

import androidx.fragment.app.Fragment

interface AuthorizationCallback {
    fun onSignIn()
    fun onSignUp()
    fun onResetPassword()
    fun onBackButtonPressed()
    fun <T : Fragment> onFinishSignUp(fragment: T)
    fun onChangePassword()
    fun onAccountCreated(email: String)
    fun onAuthorization(email: String)
}
