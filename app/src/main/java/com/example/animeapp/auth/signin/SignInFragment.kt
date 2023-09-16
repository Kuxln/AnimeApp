package com.example.animeapp.auth.signin

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.auth.AuthFragment
import com.example.animeapp.core.AnimeApp
import com.example.animeapp.core.MyViewModelFactory
import com.example.animeapp.databinding.FragmentSignInBinding


class SignInFragment : AuthFragment<FragmentSignInBinding>(
    R.layout.fragment_sign_in
) {
    val viewModel: SignInViewModel by viewModels { MyViewModelFactory(requireActivity().applicationContext as AnimeApp) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentSignInBinding.bind(view)
        fragmentBinding.tvForgotPassword.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        fragmentBinding.tvSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG


        fragmentBinding.btnSignIn.setOnClickListener {
            val email = fragmentBinding.etEmail.text.toString()
            viewModel.onSignInClicked(email)
        }
        fragmentBinding.tvSignUp.setOnClickListener {
            fragmentCallback.onSignUpClicked()
        }

        fragmentBinding.tvForgotPassword.setOnClickListener {
            fragmentCallback.onResetPassword()
        }



        viewModel.liveData.observe(this.viewLifecycleOwner) {
            fragmentBinding.etPasswordText.setText(it.password)
        }
    }
}

