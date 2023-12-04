package com.example.animeapp.presentation.auth.signin

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentSignInBinding
import com.example.animeapp.presentation.auth.AuthFragment
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory


class SignInFragment : AuthFragment<FragmentSignInBinding>(
    R.layout.fragment_sign_in
) {
    private val viewModel: SignInViewModel by viewModels { AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignInBinding.bind(view)
        binding.tvForgotPassword.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.tvSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmailText.text.toString()
            val password = binding.etPasswordText.text.toString()

            viewModel.onSignInClicked(email, password)
        }

        binding.tvSignUp.setOnClickListener { fragmentCallback.onSignUp() }

        binding.tvForgotPassword.setOnClickListener { fragmentCallback.onResetPassword() }

        viewModel.liveData.observe(this.viewLifecycleOwner) {
            if (it.showError == true) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "not correct password or email",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearState()
            }
            if (it.showError == false) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Authorization completed",
                    Toast.LENGTH_SHORT
                ).show()
                fragmentCallback.onAuthorization(it.email!!)
                viewModel.addPreferenceAccount()
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
            if (it.isEmailValid == false) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Email is not Valid or length is shorter than 5 symbols",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearState()
            }
            if (it.pref_email != null) {
                binding.etEmailText.setText(it.pref_email)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getPrefs()
    }

}

