package com.example.animeapp.presentation.auth.signin

data class SignInViewState(
    var email: String? = null,
    var showError: Boolean? = null,
    var isPasswordValid: Boolean? = null,
    var isEmailValid: Boolean? = null
)