package com.example.animeapp.auth.signin

data class SignInViewState (
    var email : String? = null,
    var password : String? = null,
    var showError : Boolean? = null
)