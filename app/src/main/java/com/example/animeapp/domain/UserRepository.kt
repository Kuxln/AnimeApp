package com.example.animeapp.domain

import com.example.animeapp.data.users.User
import com.example.animeapp.data.users.UserDao
import com.example.animeapp.presentation.core.hashing
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun findUser(email: String, password: String): User? {
        return userDao.findUser(email, password)
    }

    fun findUserByEmail(email: String): User? {
        return userDao.findUserByEmail(email)
    }

    fun insert(user: User) {
        userDao.insert(user)
    }

    fun update(user: User) {
        userDao.update(user)
    }

}