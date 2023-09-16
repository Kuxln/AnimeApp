package com.example.animeapp.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentView, SignInFragment())
                .commit()
        }
    }

    override fun onSignIn()  = launchFragment(SignInFragment())

    override fun onSignUp() = launchFragment(SignUpFragment())

    override fun onResetPassword() = launchFragment(ChangePasswordFragment())

    override fun onFinishSignUp() = launchFragment(SignUpFinishFragment())

    override fun onChangePassword() = launchFragment(ChangePasswordFragment())

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(fragment::class.java.name)
            .replace(R.id.fragmentView, fragment)
            .commit()
    }

    override fun onBackButtonPressed() {
        onBackPressed()
    }

}