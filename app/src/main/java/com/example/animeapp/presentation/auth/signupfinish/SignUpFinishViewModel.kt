package com.example.animeapp.presentation.auth.signupfinish

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeapp.data.users.AppDatabase
import com.example.animeapp.data.users.User
import com.example.animeapp.data.users.UserGender
import com.example.animeapp.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpFinishViewModel @Inject constructor(
    private val userRepo: UserRepository
) : ViewModel() {
    val liveData: LiveData<SignUpFinishState> get() = _liveData
    private val _liveData = MutableLiveData<SignUpFinishState>()

    private val signUpFinishState = SignUpFinishState()

    private lateinit var user: User


    fun onFragmentCreated(email: String) {
        signUpFinishState.username = null
        signUpFinishState.profileImageURI = null

        user = userRepo.findUserByEmail(email)!!
        if (user.name != null) {
            signUpFinishState.username = user.name
        }
        if (user.profileImage != null) {
            signUpFinishState.profileImageURI = user.profileImage
        }
        _liveData.postValue(signUpFinishState)
    }

    fun onFinishProfile(number: String, name: String, gender: UserGender) {
        user.gender = gender
        user.number = number
        user.name = name
        if (signUpFinishState.profileImageURI != null) {
            user.profileImage = signUpFinishState.profileImageURI
        }
        userRepo.update(user)
        signUpFinishState.email = user.email
        _liveData.postValue(signUpFinishState)
    }

    fun onImagePicked(uri: Uri) {
        signUpFinishState.profileImageURI = uri.toString()
        _liveData.postValue(signUpFinishState)
    }

}