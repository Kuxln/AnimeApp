package com.example.animeapp.presentation.auth.signup

data class SignUpState(
    var isTermsAccepted: Boolean = false,
    var email: String? = null,
    var password: String? = null,
    var name: String? = null,
    var isEmailExist: Boolean? = null,
    var isEmailValid: Boolean? = null,
    var isPasswordValid: Boolean? = null,
    var isNameValid: Boolean? = null,
    var isFinished: Boolean? = null,
    var onCreatePrefs: Boolean? = null,
)