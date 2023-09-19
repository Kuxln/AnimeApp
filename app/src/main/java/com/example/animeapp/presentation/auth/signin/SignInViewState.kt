package com.example.animeapp.presentation.auth.signin

import com.example.animeapp.data.UserGender

data class SignInViewState(
    var email: String? = null,
    var password: String? = null,
    var showError: Boolean? = null,
    var image: String? = null,
    var gender: UserGender? = null,
    var name: String? = null,
    var number: String? = null
)