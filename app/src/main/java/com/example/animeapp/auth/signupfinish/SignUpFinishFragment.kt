package com.example.animeapp.auth.signupfinish

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.auth.AuthFragment
import com.example.animeapp.core.AnimeApp
import com.example.animeapp.core.MyViewModelFactory
import com.example.animeapp.databinding.FragmentSignUpFinishBinding

class SignUpFinishFragment : AuthFragment<FragmentSignUpFinishBinding>(
    R.layout.fragment_sign_up_finish
) {
    val viewModel: SignUpFinishViewModel by viewModels { MyViewModelFactory(requireActivity().applicationContext as AnimeApp) }

    private lateinit var arrayAdapter: ArrayAdapter<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentBinding = FragmentSignUpFinishBinding.bind(view)
        fragmentBinding.btnBack.setOnClickListener { fragmentCallback.onBackButtonPressed() }
        var genders = arrayListOf("Man", "Woman", "Other")
        arrayAdapter = ArrayAdapter(view.context, R.layout.spinner_list_item, genders)
        fragmentBinding.spinnerGender.adapter = arrayAdapter
    }
}