package com.example.animeapp.modules

import android.content.Context
import androidx.room.Room
import com.example.animeapp.data.anime.AnimeApiDataSource
import com.example.animeapp.data.users.AppDatabase
import com.example.animeapp.data.users.UserDao
import com.example.animeapp.domain.AnimeRepository
import com.example.animeapp.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "anime_db"
        ).allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserRepository(userDao: UserDao) : UserRepository {
        return UserRepository(userDao)
    }
}