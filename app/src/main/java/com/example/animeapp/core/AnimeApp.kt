package com.example.animeapp.core

import android.app.Application
import androidx.room.Room

class AnimeApp : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "anime_db"
        ).build()

    }
}