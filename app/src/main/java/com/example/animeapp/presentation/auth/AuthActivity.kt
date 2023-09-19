package com.example.animeapp.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.animeapp.R
import com.example.animeapp.databinding.ActivityAuthBinding
import com.example.animeapp.presentation.auth.changepassword.ChangePasswordFragment
import com.example.animeapp.presentation.auth.signin.SignInFragment
import com.example.animeapp.presentation.auth.signup.SignUpFragment
import com.example.animeapp.presentation.auth.signupfinish.SignUpFinishFragment
import com.example.animeapp.presentation.main.MainActivity

class AuthActivity : AppCompatActivity(), AuthorizationCallback {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
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

    override fun onFinishSignUp(fragment: SignUpFinishFragment) = launchFragment(fragment)

    override fun onChangePassword() = launchFragment(ChangePasswordFragment())

    override fun onBackButtonPressed() = onBackPressed()

    override fun onAccountCreated(email: String) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("EMAIL", email)
        startActivity(intent)
        finish()
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(fragment::class.java.name)
            .replace(R.id.fragmentView, fragment)
            .commit()
    }

}