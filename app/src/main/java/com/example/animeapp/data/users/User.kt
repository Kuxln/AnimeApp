package com.example.animeapp.data.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "Users", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "number") var number: String? = null,
    @ColumnInfo(name = "gender") var gender: UserGender? = UserGender.Other,
    @ColumnInfo(name = "profile_image") var profileImage: String? = null
)

enum class UserGender(
    val representation: String
) {
    Male("Male"),
    Female("Female"),
    Other("Other")
}

class Converters {

    @TypeConverter
    fun toUserGender(value: String) = enumValueOf<UserGender>(value)

    @TypeConverter
    fun fromUserGender(value: UserGender) = value.name
}