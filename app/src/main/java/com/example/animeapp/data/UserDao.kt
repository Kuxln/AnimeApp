package com.example.animeapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM Users WHERE email = :email")
    fun findUserByEmail(email: String): User?

    @Query("SELECT * FROM Users WHERE email = :email AND password = :passHash")
    fun findUser(email: String, passHash: String): User?

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)
//
//    @Update(entity = User::class)
//    fun update(obj: UserFinishFields)
//
//    @Entity
//    data class UserFinishFields(
//        @ColumnInfo(name = "name") val name: String?,
//        @ColumnInfo(name = "number") val number: String?,
//        @ColumnInfo(name = "gender") val gender: Int?
//    )


}