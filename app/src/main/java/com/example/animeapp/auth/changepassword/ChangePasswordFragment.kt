package com.example.animeapp.auth.changepassword

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.auth.AuthFragment
import com.example.animeapp.core.AnimeApp
import com.example.animeapp.core.MyViewModelFactory
import com.example.animeapp.databinding.FragmentChangePasswordBinding

class ChangePasswordFragment : AuthFragment<FragmentChangePasswordBinding>(
    R.layout.fragment_change_password
) {
    val viewModel : ChangePasswordViewModel by viewModels{ MyViewModelFactory(requireActivity().applicationContext as AnimeApp) }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentChangePasswordBinding.bind(view)
        fragmentBinding.btnBack.setOnClickListener { fragmentCallback.onBackButtonPressed() }

        fragmentBinding.btnSignIn.setOnClickListener{
            fragmentCallback.onCreateNewPassword()
            viewModel.onCreateNewPasswordClicked()
        }

    }

}