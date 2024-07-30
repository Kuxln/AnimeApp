package com.example.animeapp.presentation.auth.signin

data class SignInViewState(
    var email: String? = null,
    var password: String? = null,
    var isPasswordValid: Boolean? = null,
    var isEmailValid: Boolean? = null,
    var prefsEmail: String? = null,
    var success: Boolean? = null,
)