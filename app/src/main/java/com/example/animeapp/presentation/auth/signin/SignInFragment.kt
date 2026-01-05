package com.example.animeapp.presentation.auth.signin

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentSignInBinding
import com.example.animeapp.presentation.auth.AuthFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : AuthFragment<FragmentSignInBinding>(
    R.layout.fragment_sign_in
) {
    private val viewModel by viewModels<SignInViewModel>()

    override fun onStart() {
        super.onStart()
        val prefs = requireActivity().getSharedPreferences("AUTH_PREFERENCES", Context.MODE_PRIVATE)
        prefs.getString("LOGIN", "")?.let { fragmentCallback.onAuthorization(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignInBinding.bind(view)

        with(binding) {
            tvForgotPassword.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            tvSignUp.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            etEmailText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) viewModel.invalidateEmail(binding.etEmailText.text.toString())
            }
            etPasswordText.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) viewModel.invalidatePassword(binding.etEmailText.text.toString())
            }

            btnSignIn.setOnClickListener {
                viewModel.invalidateEmail(binding.etEmailText.text.toString())
                viewModel.invalidatePassword(binding.etEmailText.text.toString())
                viewModel.onSignInClicked()
            }

            tvSignIn.setOnClickListener { fragmentCallback.onSignIn() }
            tvSignUp.setOnClickListener { fragmentCallback.onSignUp() }
            tvForgotPassword.setOnClickListener { fragmentCallback.onResetPassword() }
        }

        viewModel.liveData.observe(this.viewLifecycleOwner) { state ->
            if (state.success == true) {
                createToast("Authorization completed")
                fragmentCallback.onAuthorization(state.email.orEmpty())
                viewModel.addPreferenceAccount()
            }

            if (state.success == false) createToast("not correct password or email")
            if (state.isPasswordValid == false) createToast("Password is shorter than 8 symbols")
            if (state.isEmailValid == false) createToast("Email is not Valid or length is shorter than 5 symbols")
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
