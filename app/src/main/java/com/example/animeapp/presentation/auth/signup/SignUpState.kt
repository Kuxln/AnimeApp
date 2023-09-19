package com.example.animeapp.presentation.auth.signup

data class SignUpState (
    var email : String? = null,
    var name : String? = null,
    var password : String? = null,
    var isTermsAccepted : Boolean? = null
) {

}