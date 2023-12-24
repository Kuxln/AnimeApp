package com.example.animeapp.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animeapp.presentation.anime.AnimeViewModel
import com.example.animeapp.presentation.anime.selectedtitle.EpisodesViewModel
import com.example.animeapp.presentation.auth.changepassword.ChangePasswordViewModel
import com.example.animeapp.presentation.auth.signin.SignInViewModel
import com.example.animeapp.presentation.auth.signup.SignUpViewModel
import com.example.animeapp.presentation.auth.signupfinish.SignUpFinishViewModel
import com.example.animeapp.presentation.manga.MangaViewModel
import com.example.animeapp.presentation.profile.ProfileViewModel
import com.example.animeapp.presentation.reels.ReelsViewModel
import com.example.animeapp.presentation.search.SearchViewModel

class AppViewModelFactory(
    private val app: AnimeApp
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel(app.db, app) as T
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(app.db) as T
            modelClass.isAssignableFrom(SignUpFinishViewModel::class.java) -> SignUpFinishViewModel(app.db) as T
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> ChangePasswordViewModel(app.db) as T

            modelClass.isAssignableFrom(AnimeViewModel::class.java) -> AnimeViewModel(app.animeApi) as T
            modelClass.isAssignableFrom(MangaViewModel::class.java) -> MangaViewModel(app.mangaApi) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel() as T
            modelClass.isAssignableFrom(ReelsViewModel::class.java) -> ReelsViewModel() as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel() as T

            modelClass.isAssignableFrom(EpisodesViewModel::class.java) -> EpisodesViewModel(app.animeApi) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}