package com.example.animeapp.domain

import com.example.animeapp.data.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
}