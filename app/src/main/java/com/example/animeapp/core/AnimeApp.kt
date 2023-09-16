package com.example.animeapp.core

import android.app.Application

class AnimeApp : Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getInstance(applicationContext)!!
    }
}