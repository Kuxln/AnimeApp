package com.example.animeapp.presentation.core

import android.app.Application
import com.example.animeapp.data.AppDatabase

class AnimeApp : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getInstance(applicationContext)!!
    }
}