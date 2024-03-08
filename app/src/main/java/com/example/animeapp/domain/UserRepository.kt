package com.example.animeapp.domain

import com.example.animeapp.data.users.UserDao
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
}