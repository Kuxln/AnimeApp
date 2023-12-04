package com.example.animeapp.presentation.auth.signupfinish

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.example.animeapp.R
import com.example.animeapp.data.UserGender
import com.example.animeapp.databinding.FragmentSignUpFinishBinding
import com.example.animeapp.presentation.auth.AuthFragment
import com.example.animeapp.presentation.core.AnimeApp
import com.example.animeapp.presentation.core.AppViewModelFactory

class SignUpFinishFragment : AuthFragment<FragmentSignUpFinishBinding>(
    R.layout.fragment_sign_up_finish
) {
    val viewModel: SignUpFinishViewModel by viewModels { AppViewModelFactory(requireActivity().applicationContext as AnimeApp) }

    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    val signUpFinishState = SignUpFinishState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                //onAvatarImageSet
                viewModel.onImagePicked(uri)
            }
        }

        viewModel.onFragmentCreated(getUserEmail())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSignUpFinishBinding.bind(view)
        binding.btnBack.setOnClickListener { fragmentCallback.onBackButtonPressed() }
        val genders = arrayListOf("Man", "Woman", "Other")
        arrayAdapter = ArrayAdapter(view.context, R.layout.spinner_list_item, genders)
        binding.spinnerGender.adapter = arrayAdapter


        binding.btnCompleteProfile.setOnClickListener {
            val number = binding.etPhoneNumberText.text.toString()
            val name = binding.etNameText.text.toString()
            val gender = when (binding.spinnerGender.selectedItem.toString()) {
                "Man" -> UserGender.Male
                "Woman" -> UserGender.Female
                "Other" -> UserGender.Other
                else -> {
                    return@setOnClickListener
                }
            }

            viewModel.onFinishProfile(number, name, gender)
        }

        binding.pickImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        viewModel.liveData.observe(this.viewLifecycleOwner) {
            if (it.profileImageURI != null) {
                binding.userImage.setImageURI(it.profileImageURI!!.toUri())
            }
            if (it.username != null) {
                binding.etNameText.setText(it.username)
            }
            if (it.email != null) {
                fragmentCallback.onAccountCreated(it.email!!)
            }
        }

    }


    private fun getUserEmail(): String = requireArguments().getString(ARG_USER_EMAIL)!!

    companion object {
        @JvmStatic
        private val ARG_USER_EMAIL = "ARG_USER_EMAIL"

        @JvmStatic
        fun newInstance(userEmail: String): SignUpFinishFragment {
            val args: Bundle = Bundle().apply {
                putString(ARG_USER_EMAIL, userEmail)
            }
            val fragment = SignUpFinishFragment()
            fragment.arguments = args
            return fragment
        }
    }
}