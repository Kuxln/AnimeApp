package com.example.animeapp.presentation.core

import android.app.Application
import com.example.animeapp.data.AnimeApi
import com.example.animeapp.data.AnimeApiDataSource
import com.example.animeapp.data.AppDatabase
import com.example.animeapp.data.MangaApiDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class AnimeApp : Application() {
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