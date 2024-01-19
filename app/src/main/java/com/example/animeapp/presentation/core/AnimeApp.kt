package com.example.animeapp.presentation.core

import androidx.multidex.MultiDexApplication
import com.example.animeapp.data.AnimeApiDataSource
import com.example.animeapp.data.AppDatabase
import com.example.animeapp.data.MangaApiDataSource
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnimeApp : MultiDexApplication()