package com.example.animeapp.presentation.core

import androidx.viewbinding.ViewBinding

abstract class MainActivityFragment<B>(
    layout: Int
) : BaseFragment<B>(layout) where B : ViewBinding {

}