package com.example.animeapp.presentation.auth.signup

data class SignUpState(
    var email: String? = null,
    var isTermsAccepted: Boolean? = null,
    var isEmailValid: Boolean? = null,
    var isEmailExist: Boolean? = null,
    var isPasswordValid: Boolean? = null,
    var isNameValid: Boolean? = null
)