package com.example.animeapp.presentation.core

import androidx.multidex.MultiDexApplication
import com.example.animeapp.data.AnimeApiDataSource
import com.example.animeapp.data.AppDatabase
import com.example.animeapp.data.MangaApiDataSource
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnimeApp : MultiDexApplication() {
    lateinit var db: AppDatabase
    lateinit var animeApi: AnimeApiDataSource
    lateinit var mangaApi: MangaApiDataSource
    override fun onCreate() {
        super.onCreate()

        db = AppDatabase.getInstance(applicationContext)!!
        animeApi = AnimeApiDataSource()
        mangaApi = MangaApiDataSource()
    }
}