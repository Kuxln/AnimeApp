package com.example.animeapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            val email= extras.getString("EMAIL")
            binding.acctextview.text = email
        }
    }



}
