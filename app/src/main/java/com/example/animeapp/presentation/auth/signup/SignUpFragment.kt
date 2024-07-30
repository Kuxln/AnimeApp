package com.example.animeapp.presentation.auth.signup

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentSignUpBinding
import com.example.animeapp.presentation.auth.AuthFragment
import com.example.animeapp.presentation.auth.signupfinish.SignUpFinishFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : AuthFragment<FragmentSignUpBinding>(
    R.layout.fragment_sign_up
) {
    val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignUpBinding.bind(view)
        with(binding) {
            tvTermsAndConditions.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            tvSignIn.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            etEmailText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) viewModel.invalidateEmail(binding.etEmailText.text.toString())
            }
            etNameText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) viewModel.invalidateName(binding.etNameText.text.toString())
            }
            etPasswordText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) viewModel.invalidatePassword(binding.etEmailText.text.toString())
            }
            btnSignUp.setOnClickListener {
                viewModel.invalidateEmail(binding.etEmailText.text.toString())
                viewModel.invalidateName(binding.etNameText.text.toString())
                viewModel.invalidatePassword(binding.etEmailText.text.toString())
                viewModel.onSignUpClicked()
            }

            tvSignIn.setOnClickListener { fragmentCallback.onSignIn() }
            checkboxAgree.setOnClickListener { viewModel.onConditionsClicked() }
        }
        viewModel.liveData.observe(this.viewLifecycleOwner) { state ->
            if (state.isEmailValid == false) createToast("Email is not Valid or length is shorter than 5 symbols")
            if (state.isEmailExist == true) createToast("Account by this email already exists")
            if (state.isPasswordValid == false) createToast("Password is shorter than 8 symbols")
            if (state.isNameValid == false) createToast("Name is shorter than 2 symbols")

            if (state.isFinished == true) {
                createToast("Registration successful")
                fragmentCallback.onFinishSignUp(SignUpFinishFragment.newInstance(state.email.orEmpty()))
            }
        }
    }

    private fun createToast(text: String) {
        Toast.makeText(
            requireActivity().applicationContext,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
}