package com.jahangiri.passwordgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jahangiri.passwordgenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //binding =>
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}