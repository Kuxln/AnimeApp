package com.example.animeapp.core

import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM Users WHERE email = :email AND password = :passHash")
    fun findUser(email : String, passHash : String) : User?
    @Insert
    fun insert(vararg users: User)
}