package com.example.animeapp.presentation.core

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B>(
    layout: Int
) : Fragment(layout) where B : ViewBinding{
    protected lateinit var fragmentBinding: B
}
