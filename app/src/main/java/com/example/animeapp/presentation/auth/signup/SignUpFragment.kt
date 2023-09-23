package com.example.animeapp.presentation.auth.signup

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentSignUpBinding
import com.example.animeapp.presentation.auth.AuthFragment
import com.example.animeapp.presentation.auth.signupfinish.SignUpFinishFragment
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory

class SignUpFragment : AuthFragment<FragmentSignUpBinding>(
    R.layout.fragment_sign_up
) {
    val viewModel: SignUpViewModel by viewModels { AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentSignUpBinding.bind(view)
        fragmentBinding.tvTermsAndConditions.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        fragmentBinding.tvSignIn.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        fragmentBinding.tvSignIn.setOnClickListener { fragmentCallback.onSignIn() }

        fragmentBinding.btnSignUp.setOnClickListener {
            val email = fragmentBinding.etEmailText.text.toString()
            val name = fragmentBinding.etNameText.text.toString()
            val password = fragmentBinding.etPasswordText.text.toString()
            viewModel.onSignUpClicked(email, name, password)
        }

        // TODO:  fragmentBinding.checkboxAgree.setOnCheckedChangeListener { }
        fragmentBinding.tvTermsAndConditions.setOnClickListener { }


        viewModel.liveData.observe(this.viewLifecycleOwner) {
            if (it.isEmailValid == false) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Email is not Valid or length is shorter than 5 symbols",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearState()
            }
            if (it.isEmailExist == true) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Account by this email already exists",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearState()
            }
            if (it.isEmailExist == false) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Registration successful",
                    Toast.LENGTH_SHORT
                ).show()
                fragmentCallback.onFinishSignUp(SignUpFinishFragment.newInstance(it.email!!))
                viewModel.clearState()
            }
            if (it.isPasswordValid == false) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Password is shorter than 8 symbols",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearState()
            }
            if (it.isNameValid == false) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Name is shorter than 2 symbols",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearState()
            }
        }
    }


}