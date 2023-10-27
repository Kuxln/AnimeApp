package com.example.animeapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animeapp.R
import com.example.animeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val email = bundle!!.getString("EMAIL")
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActivityFragmentContainer, TabsFragment())
            .commit()
    }

//    override fun onBackPressed() {
//
//        if (!fragment.onBack) {
//            super.onBackPressed()
//        }
//    }
}


