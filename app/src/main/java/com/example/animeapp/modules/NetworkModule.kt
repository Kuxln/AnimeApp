package com.example.animeapp.modules

import com.example.animeapp.data.anime.AnimeApi
import com.example.animeapp.data.anime.AnimeApiDataSource
import com.example.animeapp.data.manga.MangaApi
import com.example.animeapp.data.manga.MangaApiDataSource
import com.example.animeapp.domain.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kitsu.io/api/edge/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAnimeApi(retrofit: Retrofit): AnimeApi {
        return retrofit.create(AnimeApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAnimeDataSource(api: AnimeApi): AnimeApiDataSource {
        return AnimeApiDataSource(api)
    }

    @Singleton
    @Provides
    fun provideMangaApi(retrofit: Retrofit): MangaApi {
        return retrofit.create(MangaApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMangaDataSource(api: MangaApi): MangaApiDataSource {
        return MangaApiDataSource(api)
    }

    @Singleton
    @Provides
    fun provideAnimeRepository(api: AnimeApiDataSource) : AnimeRepository {
        return AnimeRepository(api)
    }
}