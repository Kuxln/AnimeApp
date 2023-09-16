package com.example.animeapp.auth

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.animeapp.core.BaseFragment

abstract class AuthFragment<B>(
    layout: Int
) : BaseFragment<B>(layout) where B : ViewBinding {

    protected lateinit var fragmentCallback: AuthorizationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentCallback = activity as AuthorizationCallback
    }
}