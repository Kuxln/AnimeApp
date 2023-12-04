package com.example.animeapp.presentation.auth.changepassword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.databinding.FragmentChangePasswordBinding
import com.example.animeapp.presentation.auth.AuthFragment
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory

class ChangePasswordFragment : AuthFragment<FragmentChangePasswordBinding>(
    R.layout.fragment_change_password
) {
    val viewModel: ChangePasswordViewModel by viewModels { AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentChangePasswordBinding.bind(view)
        binding.btnBack.setOnClickListener { fragmentCallback.onBackButtonPressed() }

        binding.btnChangePassword.setOnClickListener {

            val password = binding.etPasswordText.text.toString()
            val passwordRepeated = binding.etConfirmPasswordText.text.toString()
            val email = binding.etEmailText.text.toString()

            viewModel.onResetPasswordClicked(email, password, passwordRepeated)
        }
        viewModel.liveData.observe(this.viewLifecycleOwner) {
            if (it.error == true) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    "Password length is too short",
                    Toast.LENGTH_LONG
                ).show()
                it.error = false
            }
        }
    }


}
