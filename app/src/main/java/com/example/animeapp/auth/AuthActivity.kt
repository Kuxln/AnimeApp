package com.example.animeapp.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animeapp.R
import com.example.animeapp.auth.changepassword.ChangePasswordFragment
import com.example.animeapp.auth.signin.SignInFragment
import com.example.animeapp.auth.signup.SignUpFragment
import com.example.animeapp.auth.signupfinish.SignUpFinishFragment
import com.example.animeapp.databinding.ActivityAccountLoginSignupBinding

class AuthActivity : AppCompatActivity(), AuthorizationCallback {
    private lateinit var binding: ActivityAccountLoginSignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountLoginSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToSignIn()
    }

    override fun onSignInClicked() = navigateToSignIn()

    override fun onSignUpClicked() = navigateToSignUp()

    override fun onResetPassword() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(ChangePasswordFragment::class.java.name)
            .replace(R.id.fragmentView, ChangePasswordFragment())
            .commit()
    }

    override fun onBackButtonPressed() {
        onBackPressed()
    }

    override fun onFinishSignUpClicked() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(SignUpFinishFragment::class.java.name)
            .replace(R.id.fragmentView, SignUpFinishFragment())
            .commit()
    }

    override fun onCreateNewPassword() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(ChangePasswordFragment::class.java.name)
            .replace(R.id.fragmentView, ChangePasswordFragment())
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }

    private fun navigateToSignUp() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(SignUpFragment::class.java.name)
            .replace(R.id.fragmentView, SignUpFragment())
            .commit()

    }

    private fun navigateToSignIn() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(SignInFragment::class.java.name)
            .replace(R.id.fragmentView, SignInFragment())
            .commit()
    }

}