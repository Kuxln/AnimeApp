package com.example.animeapp.auth.signup

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.auth.AuthFragment
import com.example.animeapp.core.AnimeApp
import com.example.animeapp.core.MyViewModelFactory
import com.example.animeapp.databinding.FragmentSignUpBinding

class SignUpFragment : AuthFragment<FragmentSignUpBinding>(
    R.layout.fragment_sign_up
) {
    val viewModel: SignUpViewModel by viewModels { MyViewModelFactory(requireActivity().applicationContext as AnimeApp) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentSignUpBinding.bind(view)
        fragmentBinding.tvTermsAndConditions.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        fragmentBinding.tvSignIn.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        fragmentBinding.tvSignIn.setOnClickListener { fragmentCallback.onSignInClicked() }

        fragmentBinding.btnSignUp.setOnClickListener {
            val email = fragmentBinding.etEmailText.text.toString()
            val name = fragmentBinding.etNameText.text.toString()
            val password = fragmentBinding.etPasswordText.text.toString()
            viewModel.onSignUpClicked(email, name, password)
        }
        fragmentBinding.tvTermsAndConditions.setOnClickListener { }

        viewModel.liveData.observe(this.viewLifecycleOwner) {

        }
    }

}