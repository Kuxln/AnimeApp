package com.example.animeapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animeapp.R
import com.example.animeapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tabsFragment: TabsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tabsFragment = TabsFragment()
        val bundle = intent.extras
        bundle!!.getString("EMAIL")
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainActivityFragmentContainer, tabsFragment)
            .commit()
    }

    override fun onBackPressed() {
        if (!tabsFragment.onBack()) {
            super.onBackPressed()
        }
    }
}
